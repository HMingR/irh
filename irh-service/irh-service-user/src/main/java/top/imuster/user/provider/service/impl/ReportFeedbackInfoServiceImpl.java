package top.imuster.user.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendEmailDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.examine.HuaweiModerationImageUtil;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.life.api.service.ForumServiceFeignApi;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.user.api.enums.FeedbackEnum;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.dao.ReportFeedbackInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ReportFeedbackInfoService;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private OrderServiceFeignApi orderServiceFeignApi;

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Resource
    UserInfoService userInfoService;

    @Autowired
    HuaweiModerationImageUtil huaweiModerationImageUtil;


    @Override
    public BaseDao<ReportFeedbackInfo, Long> getDao() {
        return this.reportFeedbackInfoDao;
    }

    @Override
    public void processReport(ReportFeedbackInfo condition, Long userId){
        reportFeedbackInfoDao.updateByKey(condition);
        if(condition.getResult() == 1){
            return;
        }
        ReportFeedbackInfo info = reportFeedbackInfoService.selectEntryList(condition.getId()).get(0);
        SendUserCenterDto target = new SendUserCenterDto();
        target.setDate(DateUtil.now());
        Long sendToId = getSendToId(info.getType(), info.getTargetId());
        target.setToId(sendToId);
        if(condition.getResult() == 3){
            //警告并删除相关内容
            target.setContent("您在irh中发布的" + FeedbackEnum.getNameByType(info.getType()) + "被人举报，经过核实举报属实。如果再次发现类似情况，您的账号将被冻结");
        }else if(condition.getResult() == 4){
            //冻结账号

            //将结果反馈给举报人
            List<Long> reporterIds = getReporterIdByTargetId(condition.getTargetId(), condition.getType());
            SendUserCenterDto temp = new SendUserCenterDto();
            temp.setContent("您举报的关于" + FeedbackEnum.getNameByType(info.getType()) + ":" + info.getTargetId() + "的信息已经被管理员成功处理，已经将相关账号进行冻结。感谢您的及时反馈");
            temp.setDate(DateUtil.now());
            temp.setNewsType(60);
            for (Long reporterId : reporterIds) {
                temp.setToId(reporterId);
                generateSendMessageService.sendToMq(temp);
            }

            //给被封的人发邮件
            SendEmailDto customer = new SendEmailDto();
            customer.setDate(DateUtil.now());
            customer.setSubject("irh关于冻结账号的通知");
            String emailById = userInfoService.getEmailById(info.getCustomerId());
            if(StringUtils.isBlank(emailById)){
                log.info("根据id{}查询会员email失败",info.getCustomerId());
            }
            customer.setContent("由于您多次违反irh平台的相关规定或多次被用户举报并核实，您的账号已经被冻结。请联系管理员取消冻结");
            customer.setEmail(emailById);
            generateSendMessageService.sendToMq(customer);
        }
        deleteByCondition(info, userId);
    }

    /**
     * @Author hmr
     * @Description 根据被举报对象的id获得所有举报该对象的用户id
     * @Date: 2020/3/2 12:03
     * @param id
     * @reture: java.util.List<java.lang.Long>
     **/
    public List<Long> getReporterIdByTargetId(Long id, Integer type){
        HashMap<String, String> params = new HashMap<>();
        params.put("targetId", String.valueOf(id));
        params.put("type", String.valueOf(type));
        List<ReportFeedbackInfo> resInfo = reportFeedbackInfoDao.selectAllReportByTargetId(params);
        ArrayList<Long> ids = new ArrayList<>(resInfo.size());
        resInfo.stream().forEach(reportFeedbackInfo -> {
            ids.add(reportFeedbackInfo.getCustomerId());
        });
        return ids;
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
            orderServiceFeignApi.deleteProductEvaluate(targetId);
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
     * @Description 根据不同的type获得不同的被举报信息的发布者的id
     * @Date: 2020/1/17 10:43
     * @param type 1-商品举报 2-留言举报 3-评价举报 4-帖子举报 5-帖子留言举报
     * @param targetId
     * @reture: java.lang.String 会员email
     **/
    private Long getSendToId(Integer type, Long targetId){
        Long consumerId = null;
        if(type == 1 || type == 2){
            consumerId = goodsServiceFeignApi.getConsumerIdByType(targetId, type);
        }else if(type == 3){
            consumerId = orderServiceFeignApi.getEvaluateWriterIdById(targetId);
        }else{
            consumerId = forumServiceFeignApi.getUserIdByType(targetId, type);
        }
        if(consumerId == null){
            log.info("没有在{}表中找到id为{}的信息",type, targetId);
            throw new UserException("操作失败,请刷新后重试或联系管理员");
        }
        return consumerId;
    }

    @Override
    public Page<ReportFeedbackInfo> list(Page<ReportFeedbackInfo> page) {
        if(page.getSearchCondition() == null){
            page.setSearchCondition(new ReportFeedbackInfo());
        }
        ReportFeedbackInfo condition = page.getSearchCondition();
        Integer count = reportFeedbackInfoDao.selectEntryListCount(condition);
        page.setTotalCount(count);
        condition.setStartIndex(page.getStartIndex());
        condition.setEndIndex(page.getEndIndex());
        List<ReportFeedbackInfo> res = reportFeedbackInfoDao.selectListByCondition(condition);
        page.setData(res);
        return page;
    }

    @Override
    public Message<String> processReportByTargetId(Long targetId, Integer result, String remark, Long userId) {
        ReportFeedbackInfo condition = new ReportFeedbackInfo();
        condition.setTargetId(targetId);
        condition.setResult(result);
        condition.setRemark(remark);
        condition.setProcessId(userId);
        reportFeedbackInfoDao.updateByTargetId(condition);
        return Message.createBySuccess();
    }

    @Override
    public Message<List<ReportFeedbackInfo>> getDetailsByTargetId(Long targetId, Integer type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("targetId", String.valueOf(targetId));
        params.put("type", String.valueOf(type));
        List<ReportFeedbackInfo> res = reportFeedbackInfoDao.selectAllReportByTargetId(params);
        return Message.createBySuccess(res);
    }

    @Override
    public Message<String> checkPic(String pciUri) throws IOException {
        String[] strings = new String[1];
        strings[0] = pciUri;
        String res = huaweiModerationImageUtil.imageContentBatchCheck(strings);
        if("NULL".equalsIgnoreCase(res)){
            return Message.createBySuccess("请等待管理员审核");
        }else if(StringUtils.isBlank(res)){
            return Message.createBySuccess();
        }
        return Message.createByError("图片审核不通过,请更换图片");
    }
}