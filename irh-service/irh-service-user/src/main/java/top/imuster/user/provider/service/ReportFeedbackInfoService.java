package top.imuster.user.provider.service;

import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.user.api.pojo.ReportFeedbackInfo;

import java.text.ParseException;
import java.util.List;

/**
 * ReportFeedbackInfoService接口
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
public interface ReportFeedbackInfoService extends BaseService<ReportFeedbackInfo, Long> {

    /**
     * @Description: 管理员处理用户提交的举报
     * @Author: hmr
     * @Date: 2020/1/11 16:35
     * @param reportFeedbackInfo
     * @reture: void
     **/
    void processReport(ReportFeedbackInfo reportFeedbackInfo);

    /**
     * @Author hmr
     * @Description 高级分页条件查询
     * @Date: 2020/1/16 11:53
     * @param page
     * @reture: java.util.List<top.imuster.user.api.pojo.ReportFeedbackInfo>
     *
     * @return*/
    Page<ReportFeedbackInfo> statistic(Page<ReportFeedbackInfo> page);

     /**
      * @Author hmr
      * @Description 发送多个消息
      * @Date: 2020/1/16 19:04
      * @param sendMessageDto
      * @reture: void
      **/
     void generateSendMessage(List<SendMessageDto> sendMessageDto) throws ParseException;

     /**
      * @Author hmr
      * @Description 发送单个消息
      * @Date: 2020/1/16 20:10
      * @param sendMessageDto
      * @reture: void
      **/
     void generateSendMessage(SendMessageDto sendMessageDto, ReportFeedbackInfo reportFeedbackInfo) throws ParseException;
}