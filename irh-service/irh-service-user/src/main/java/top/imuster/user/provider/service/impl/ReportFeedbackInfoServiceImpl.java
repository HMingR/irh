package top.imuster.user.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.life.api.service.ForumServiceFeignApi;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.user.api.enums.FeedbackEnum;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.dao.ReportFeedbackInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.UserInfoService;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ReportFeedbackInfoService 实现类
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
@Service("reportFeedbackInfoService")
@Slf4j
public class ReportFeedbackInfoServiceImpl extends BaseServiceImpl<ReportFeedbackInfo, Long> implements ReportFeedbackInfoService {

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Resource
    private ReportFeedbackInfoDao reportFeedbackInfoDao;

    @Resource
    private ReportFeedbackInfoService reportFeedbackInfoService;

    @Autowired
    private ForumServiceFeignApi forumServiceFeignApi;

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Resource
    UserInfoService userInfoService;


    @Override
    public BaseDao<ReportFeedbackInfo, Long> getDao() {
        return this.reportFeedbackInfoDao;
    }

    @Override
    public void processReport(ReportFeedbackInfo condition, Long userId) throws Exception {
        reportFeedbackInfoDao.updateByKey(condition);
        if(condition.getResult() == 1){
            return;
        }
        ReportFeedbackInfo info = reportFeedbackInfoService.selectEntryList(condition.getId()).get(0);
        SendMessageDto target = new SendMessageDto();
        target.setSourceId(-1L);
        target.setSendDate(DateUtils.current());
        String sendToId = getSendToId(info.getType(), info.getTargetId());
        target.setSendTo(sendToId);
        if(condition.getResult() == 3 || condition.getResult() == 4){
            target.setType(MqTypeEnum.CENTER);
            target.setTopic("警告");
            target.setBody("您在irh中发布的" + FeedbackEnum.getNameByType(info.getType()) + "被人举报，经过核实举报属实。如果再次发现类似情况，您的账号将被冻结");
            generateSendMessageService.sendToMq(target);
        }else if(condition.getResult() == 5){
            ArrayList<SendMessageDto> sendMessageDtos = new ArrayList<>();
            SendMessageDto customerMessage = new SendMessageDto();
            customerMessage.setSourceId(-1L);
            customerMessage.setSendDate(DateUtils.current());
            customerMessage.setType(MqTypeEnum.CENTER);
            customerMessage.setBody("您于" + info.getCreateTime() + "举报的关于" + FeedbackEnum.getNameByType(info.getType()) + ":" + info.getTargetId() + "的信息已经被管理员成功处理，已经将相关账号进行冻结。感谢您的及时反馈");
            String emailById = userInfoService.getEmailById(info.getCustomerId());
            if(StringUtils.isBlank(emailById)){
                log.info("根据id{}查询会员email失败",info.getCustomerId());
            }
            customerMessage.setSendTo(emailById);
            target.setBody("由于您多次违反irh平台的相关规定或多次被用户举报并核实，您的账号已经被冻结。请联系管理员取消冻结");
            target.setType(MqTypeEnum.EMAIL);
            sendMessageDtos.add(customerMessage);
            sendMessageDtos.add(target);
            generateSendMessageService.senManyToMq(sendMessageDtos);
        }
        deleteByCondition(info, userId);
    }

    /**
     * @Author hmr
     * @Description 核事之后需要删除相关信息
     * @Date: 2020/1/17 11:17
     * @param reportFeedbackInfo
     * @param userId
     * @reture: void
     **/
    private void deleteByCondition(ReportFeedbackInfo reportFeedbackInfo, Long userId){
        Long targetId = reportFeedbackInfo.getTargetId();
        if (reportFeedbackInfo.getType() == 1) {
            //商品
            goodsServiceFeignApi.delProduct(targetId);
            log.info("编号为{}的管理员删除编号为{}的商品",userId, targetId);
        }else if(reportFeedbackInfo.getType() == 2){
            //商品留言
            goodsServiceFeignApi.deleteProductMessageById(targetId);
            log.info("编号为{}的管理员删除编号为{}的留言", userId, targetId);
        }else if(reportFeedbackInfo.getType() == 3){
            //商品评价
            goodsServiceFeignApi.deleteProductEvaluate(targetId);
            log.info("编号为{}的管理员删除编号为{}的评价",userId, targetId);
        }else if(reportFeedbackInfo.getType() == 4){
            //论坛帖子
            forumServiceFeignApi.adminDeleteArticle(targetId);
            log.info("编号为{}的管理员删除编号为{}的文章",userId, targetId);
        }else if(reportFeedbackInfo.getType() == 5){
            //论坛帖子留言
            forumServiceFeignApi.adminDeleteArticleReview(targetId);
            log.info("编号为{}的管理员删除了编号为{}的帖子留言", userId, targetId);
        }
    }

    /**
     * @Author hmr
     * @Description 根据不同的type获得不同的被举报信息的发布者的email
     * @Date: 2020/1/17 10:43
     * @param type 1-商品举报 2-留言举报 3-评价举报 4-帖子举报 5-帖子留言举报
     * @param targetId
     * @reture: java.lang.String 会员email
     **/
    private String getSendToId(Integer type, Long targetId){
        Long consumerId;
        if(type <= 3){
            consumerId = goodsServiceFeignApi.getConsumerIdByType(targetId, type);
        }else{
            consumerId = forumServiceFeignApi.getUserIdByType(targetId, type);
        }
        if(consumerId == null){
            log.info("没有在{}表中找到id为{}的信息",type, targetId);
            throw new UserException("操作失败,请刷新后重试或联系管理员");
        }
        return userInfoService.getEmailById(consumerId);
    }

    @Override
    public Page<ReportFeedbackInfo> statistic(Page<ReportFeedbackInfo> page) {
        ReportFeedbackInfo condition = page.getSearchCondition();
        condition.setEndIndex(page.getEndIndex());
        condition.setStartIndex(page.getStartIndex());
        Integer count = selectEntryListCount(condition);
        page.setTotalCount(count);
        List<ReportFeedbackInfo> reportFeedbackInfos = reportFeedbackInfoDao.selectStatisticsByCondition(condition);
        page.setData(reportFeedbackInfos);
        return page;
    }
}