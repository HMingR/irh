package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.provider.dao.ConsumerInfoDao;
import top.imuster.user.provider.service.ConsumerInfoService;

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

    @Override
    public ConsumerInfo loginByName(String name) {
        ConsumerInfo consumerInfo = new ConsumerInfo();
        return consumerInfoDao.selectEntryList(consumerInfo).get(0);
    }
}