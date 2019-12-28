package top.imuster.order.provider.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.utils.JwtTokenUtil;
import top.imuster.common.core.utils.UuidUtils;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.api.dto.ProductOrderDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.dao.OrderInfoDao;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * OrderInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, Long> implements OrderInfoService {

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Resource
    private OrderInfoDao orderInfoDao;

    @Override
    public BaseDao<OrderInfo, Long> getDao() {
        return this.orderInfoDao;
    }

    @Override
    public OrderInfo getOrderInfoByOrderCode(String orderCode) {
        return orderInfoDao.selectOrderByOrderCode(orderCode);
    }

    @Override
    public OrderInfo getOrderByProduct(ProductOrderDto productOrderDto, String token) throws Exception {
        ProductInfo productInfo = productOrderDto.getProductInfo();
        OrderInfo orderInfo = productOrderDto.getOrderInfo();
        boolean b = checkProduct(productInfo.getId());
        if(!b){
            throw new OrderException("该商品已经不存在,请刷新后重试");
        }
        orderInfo.setProductId(productInfo.getId());
        orderInfo.setPaymentMoney(productInfo.getSalePrice());
        orderInfo.setSalerId(productInfo.getConsumerId());
        orderInfo.setOrderCode(String.valueOf(UuidUtils.nextId()));
        orderInfo.setBuyerId(JwtTokenUtil.getUserIdFromToken(token));
        orderInfo.setState(40);
        orderInfo.setTradeType(orderInfo.getTradeType());
        orderInfoDao.insertEntry(orderInfo);
        return orderInfo;
    }

    /**
     * @Description: 校验商品是否还有库存，如果有，则锁定该商品不让其他人再次下单
     * @Author: hmr
     * @Date: 2019/12/28 10:50
     * @param productId
     * @reture:
     **/
    private boolean checkProduct(Long productId){
        return goodsServiceFeignApi.lockStock(productId);
    }
}