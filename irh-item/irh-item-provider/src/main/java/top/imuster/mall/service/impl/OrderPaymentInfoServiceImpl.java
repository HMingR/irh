package top.imuster.mall.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.mall.dao.OrderPaymentInfoDao;
import top.imuster.mall.domain.OrderPaymentInfo;
import top.imuster.mall.service.OrderPaymentInfoService;
import top.imuster.service.base.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * OrderPaymentInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("orderPaymentInfoService")
public class OrderPaymentInfoServiceImpl extends BaseServiceImpl<OrderPaymentInfo, Long> implements OrderPaymentInfoService {

    @Resource
    private OrderPaymentInfoDao orderPaymentInfoDao;

    @Override
    public BaseDao<OrderPaymentInfo, Long> getDao() {
        return this.orderPaymentInfoDao;
    }
}