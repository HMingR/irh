package top.imuster.item.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.item.dao.OrderPaymentInfoDao;
import top.imuster.item.domain.OrderPaymentInfo;
import top.imuster.item.service.OrderPaymentInfoService;
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