package top.imuster.mall.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.mall.dao.ConsumerInterestTagRelDao;
import top.imuster.mall.domain.ConsumerInterestTagRel;
import top.imuster.mall.service.ConsumerInterestTagRelService;
import top.imuster.service.base.BaseServiceImpl;

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