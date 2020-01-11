package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.dao.ReportFeedbackInfoDao;
import top.imuster.user.provider.service.ReportFeedbackInfoService;

import javax.annotation.Resource;

/**
 * ReportFeedbackInfoService 实现类
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
@Service("reportFeedbackInfoService")
public class ReportFeedbackInfoServiceImpl extends BaseServiceImpl<ReportFeedbackInfo, Long> implements ReportFeedbackInfoService {

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
    }
}