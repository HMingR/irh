package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ReportTypeInfo;
import top.imuster.user.provider.dao.ReportTypeInfoDao;
import top.imuster.user.provider.service.ReportTypeInfoService;

import javax.annotation.Resource;

/**
 * ReportTypeInfoService 实现类
 * @author 黄明人
 * @since 2020-05-25 10:03:10
 */
@Service("reportTypeInfoService")
public class ReportTypeInfoServiceImpl extends BaseServiceImpl<ReportTypeInfo, Long> implements ReportTypeInfoService {

    @Resource
    private ReportTypeInfoDao reportTypeInfoDao;

    @Override
    public BaseDao<ReportTypeInfo, Long> getDao() {
        return this.reportTypeInfoDao;
    }
}