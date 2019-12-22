package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.InterestTagInfo;
import top.imuster.user.provider.dao.InterestTagInfoDao;
import top.imuster.user.provider.service.InterestTagInfoService;

import javax.annotation.Resource;

/**
 * InterestTagInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("interestTagInfoService")
public class InterestTagInfoServiceImpl extends BaseServiceImpl<InterestTagInfo, Long> implements InterestTagInfoService {

    @Resource
    private InterestTagInfoDao interestTagInfoDao;

    @Override
    public BaseDao<InterestTagInfo, Long> getDao() {
        return this.interestTagInfoDao;
    }
}