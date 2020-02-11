package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.life.api.pojo.ErrandOrder;
import top.imuster.life.provider.dao.ErrandOrderDao;
import top.imuster.life.provider.service.ErrandOrderService;

import javax.annotation.Resource;

/**
 * ErrandOrderService 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
@Service("errandOrderService")
public class ErrandOrderServiceImpl extends BaseServiceImpl<ErrandOrder, Long> implements ErrandOrderService {

    @Resource
    private ErrandOrderDao errandOrderDao;

    @Override
    public BaseDao<ErrandOrder, Long> getDao() {
        return this.errandOrderDao;
    }
}