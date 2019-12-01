package top.imuster.item.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.item.dao.ConsumerInfoDao;
import top.imuster.item.pojo.ConsumerInfo;
import top.imuster.item.service.ConsumerInfoService;
import top.imuster.service.base.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * ConsumerInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("consumerInfoService")
public class ConsumerInfoServiceImpl extends BaseServiceImpl<ConsumerInfo, Long> implements ConsumerInfoService {

    @Resource
    private ConsumerInfoDao consumerInfoDao;

    @Override
    public BaseDao<ConsumerInfo, Long> getDao() {
        return this.consumerInfoDao;
    }
}