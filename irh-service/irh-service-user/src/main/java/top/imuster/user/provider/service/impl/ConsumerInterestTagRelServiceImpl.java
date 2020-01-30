package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.UserInterestTagRel;
import top.imuster.user.provider.dao.UserInterestTagRelDao;
import top.imuster.user.provider.service.ConsumerInterestTagRelService;

import javax.annotation.Resource;

/**
 * ConsumerInterestTagRelService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("consumerInterestTagRelService")
public class ConsumerInterestTagRelServiceImpl extends BaseServiceImpl<UserInterestTagRel, Long> implements ConsumerInterestTagRelService {

    @Resource
    private UserInterestTagRelDao userInterestTagRelDao;

    @Override
    public BaseDao<UserInterestTagRel, Long> getDao() {
        return this.userInterestTagRelDao;
    }
}