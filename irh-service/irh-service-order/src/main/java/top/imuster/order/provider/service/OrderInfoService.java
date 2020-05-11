package top.imuster.order.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.pojo.OrderInfo;

import java.util.List;

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
    Message<OrderInfo> getOrderByProduct(OrderInfo orderInfo, Long userId, Long productId);

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
     * @Description
     * @Date: 2020/4/13 14:39
     * @param pageSize
     * @param currentPage
     * @param userId
     * @param type   1-userId作为买家   2-userId作为卖家
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.order.api.pojo.OrderInfo>>
     **/
    Message<Page<OrderInfo>> list(Integer pageSize, Integer currentPage, Long userId, Integer type);

    /**
     * @Author hmr
     * @Description 完成订单
     * @Date: 2020/4/13 16:21
     * @param orderId
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> finishOrder(Long orderId, Long userId);

    /**
     * @Author hmr
     * @Description 获得所有选择捐赠的订单的订单id、订单金额、订单版本
     * @Date: 2020/4/15 9:33
     * @param
     * @reture: java.util.List<top.imuster.order.api.pojo.OrderInfo>
     **/
    List<OrderInfo> getAllDonationOrderInfo();

    /**
     * @Author hmr
     * @Description 根据id获得订单的版本信息
     * @Date: 2020/4/15 16:15
     * @param id
     * @reture: java.lang.Integer
     **/
    Integer getOrderVersionById(Long id);

    /**
     * @Author hmr
     * @Description 生成订单编号
     * @Date: 2020/5/11 10:41
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> createOrderCode(Long currentUserIdFromCookie);
}