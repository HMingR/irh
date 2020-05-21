package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.provider.dao.ExamineRecordInfoDao;
import top.imuster.user.provider.service.ExamineRecordInfoService;

import javax.annotation.Resource;

/**
 * ExamineRecordInfoService 实现类
 * @author 黄明人
 * @since 2020-05-21 19:27:46
 */
@Service("examineRecordInfoService")
public class ExamineRecordInfoServiceImpl extends BaseServiceImpl<ExamineRecordInfo, Long> implements ExamineRecordInfoService {

    @Resource
    private ExamineRecordInfoDao examineRecordInfoDao;

    @Override
    public BaseDao<ExamineRecordInfo, Long> getDao() {
        return this.examineRecordInfoDao;
    }
}