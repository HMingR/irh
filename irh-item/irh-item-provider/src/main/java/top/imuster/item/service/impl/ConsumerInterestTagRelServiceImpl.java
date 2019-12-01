package top.imuster.item.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.item.dao.ConsumerInterestTagRelDao;
import top.imuster.item.domain.ConsumerInterestTagRel;
import top.imuster.item.service.ConsumerInterestTagRelService;
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