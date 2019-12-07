package top.imuster.user.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.user.dao.ConsumerInfoDao;
import top.imuster.service.base.BaseServiceImpl;
import top.imuster.user.pojo.ConsumerInfo;
import top.imuster.user.service.ConsumerInfoService;

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