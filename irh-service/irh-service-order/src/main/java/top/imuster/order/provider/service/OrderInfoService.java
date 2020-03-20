package top.imuster.order.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.dto.ProductOrderDto;
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

    /**
     * @Description: 根据商品信息生成订单
     * @Author: hmr
     * @Date: 2019/12/28 9:57
     * @param productInfo
     * @param token
     * @reture: top.imuster.order.api.pojo.OrderInfo
     **/
    OrderInfo getOrderByProduct(ProductOrderDto productOrderDto, Long userId);

    /**
     * @Author hmr
     * @Description 获得订单总金额的趋势图
     * @Date: 2020/3/2 15:45
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.dto.OrderTrendDto>
     **/
    Message<OrderTrendDto> getOrderAmountTrend(Integer type);

    /**
     * @Author hmr
     * @Description 获得订单数量趋势
     * @Date: 2020/3/2 16:54
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.dto.OrderTrendDto>
     **/
    Message<OrderTrendDto> getOrderTotalTrend(Integer type);

    /**
     * @Author hmr
     * @Description 修改订单
     * @Date: 2020/3/20 10:42
     * @param orderInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> editOrderInfo(OrderInfo orderInfo);
}