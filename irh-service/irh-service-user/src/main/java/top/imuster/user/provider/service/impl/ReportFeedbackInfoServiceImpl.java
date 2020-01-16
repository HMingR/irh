package top.imuster.user.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.dao.ReportFeedbackInfoDao;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.Resource;
import java.text.ParseException;
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

    @Override
    public BaseDao<ReportFeedbackInfo, Long> getDao() {
        return this.reportFeedbackInfoDao;
    }

    @Override
    public void processReport(ReportFeedbackInfo reportFeedbackInfo) {
        reportFeedbackInfoDao.updateByKey(reportFeedbackInfo);
        // todo 需要给举报人和被举报人发送消息
        if(reportFeedbackInfo.getResult() == 3){

        }
    }

    @Override
    public Page<ReportFeedbackInfo> statistic(Page<ReportFeedbackInfo> page) {
        ReportFeedbackInfo condition = page.getSearchCondition();
        condition.setEndIndex(page.getEndIndex());
        condition.setStartIndex(page.getStartIndex());
        Integer count = selectEntryListCount(condition);
        page.setTotalCount(count);
        List<ReportFeedbackInfo> reportFeedbackInfos = reportFeedbackInfoDao.selectStatisticsByCondition(condition);
        page.setResult(reportFeedbackInfos);
        return page;
    }

    @Override
    @MqGenerate(one = false)
    public void generateSendMessage(List<SendMessageDto> sendMessageDtos) throws ParseException {
        for (SendMessageDto sendMessageDto : sendMessageDtos) {
            sendMessageDto.setSendDate(DateUtils.current());
            sendMessageDto.setType(MqTypeEnum.EMAIL);
            sendMessageDto.setTopic("通知");
        }
        log.info("发送多个消息，消息总数为{}", sendMessageDtos.size());
    }

    @Override
    @MqGenerate
    public void generateSendMessage(SendMessageDto sendMessageDto, ReportFeedbackInfo reportFeedbackInfo) throws ParseException {
        sendMessageDto.setSourceId(-1L);
        sendMessageDto.setSourceType(20);
        sendMessageDto.setSendDate(DateUtils.current());
        sendMessageDto.setType(MqTypeEnum.EMAIL);
        log.info("发送一个消息，消息实体为{}", sendMessageDto);
        if (reportFeedbackInfo.getType() == 1) {
            //商品
            goodsServiceFeignApi.delProduct(reportFeedbackInfo.getTargetId());
        }else if(reportFeedbackInfo.getType() == 2){
            //留言
            goodsServiceFeignApi.deleteProductMessageById(reportFeedbackInfo.getTargetId());
        }else if(reportFeedbackInfo.getType() == 3){
            //评价
            goodsServiceFeignApi.deleteProductEvaluate(reportFeedbackInfo.getTargetId());
        }else if(reportFeedbackInfo.getType() == 4){
            //todo 删除帖子
        }
    }
}