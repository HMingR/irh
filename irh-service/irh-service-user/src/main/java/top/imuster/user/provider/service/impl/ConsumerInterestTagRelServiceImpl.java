package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ConsumerInterestTagRel;
import top.imuster.user.provider.dao.ConsumerInterestTagRelDao;
import top.imuster.user.provider.service.ConsumerInterestTagRelService;

import javax.annotation.Resource;

/**
 * ConsumerInterestTagRelService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("consumerInterestTagRelService")
public class ConsumerInterestTagRelServiceImpl extends BaseServiceImpl<ConsumerInterestTagRel, Long> implements ConsumerInterestTagRelService {

    @Resource
    private ConsumerInterestTagRelDao consumerInterestTagRelDao;

    @Override
    public BaseDao<ConsumerInterestTagRel, Long> getDao() {
        return this.consumerInterestTagRelDao;
    }
}