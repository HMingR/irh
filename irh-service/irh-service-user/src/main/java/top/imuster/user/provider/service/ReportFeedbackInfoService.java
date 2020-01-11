package top.imuster.user.provider.service;

import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.ReportFeedbackInfo;

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
}