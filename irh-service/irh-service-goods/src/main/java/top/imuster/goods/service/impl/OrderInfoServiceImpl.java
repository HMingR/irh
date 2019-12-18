package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.goods.api.pojo.OrderInfo;
import top.imuster.goods.dao.OrderInfoDao;
import top.imuster.goods.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * OrderInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, Long> implements OrderInfoService {

    @Resource
    private OrderInfoDao orderInfoDao;

    @Override
    public BaseDao<OrderInfo, Long> getDao() {
        return this.orderInfoDao;
    }
}