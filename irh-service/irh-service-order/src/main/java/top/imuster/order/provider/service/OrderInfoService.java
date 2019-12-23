package top.imuster.order.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.order.api.pojo.OrderInfo;

/**
 * OrderInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface OrderInfoService extends BaseService<OrderInfo, Long> {

    /**
     * @Description: 根据订单号查询订单
     * @Author: hmr
     * @Date: 2019/12/23 21:29
     * @param orderCode
     * @reture: top.imuster.order.api.pojo.OrderInfo
     **/
    OrderInfo getOrderInfoByOrderCode(String orderCode);

}