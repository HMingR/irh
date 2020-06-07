package top.imuster.order.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.hystrix.OrderServiceFeignApiHystrix;

/**
 * @ClassName: OrderServiceFeignApi
 * @Description: order模块提供给其他模块的接口
 * @author: hmr
 * @date: 2019/12/27 15:28
 */

@FeignClient(value = "order-service", path = "/feign/order", fallbackFactory = OrderServiceFeignApiHystrix.class)
public interface OrderServiceFeignApi {

    /**
     * @Description: 根据id查询订单信息
     * @Author: hmr
     * @Date: 2020/1/9 11:56
     * @param orderId
     * @reture: top.imuster.order.api.pojo.OrderInfo
     **/
    @GetMapping("/{orderId}")
    OrderInfo getOrderById(@PathVariable("orderId") Long orderId);

    /**
     * @Description: 分页条件查询订单
     * @Author: hmr
     * @Date: 2020/1/11 10:35
     * @param page
     * @reture: java.util.List<top.imuster.order.api.pojo.OrderInfo>
     **/
    @PostMapping
    Message<Page<OrderInfo>> orderList(@RequestBody Page<OrderInfo> page);

    /**
     * @Author hmr
     * @Description 获得订单总金额趋势图
     * @Date: 2020/3/2 15:43
     * @param type 1-最近一周  2-一个月   3-半年  4-一年
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.dto.OrderTrendDto>
     **/
    @GetMapping("/trend/amount/{type}")
    Message<OrderTrendDto> getOrderAmountTrend(@PathVariable("type") Integer type);

    /**
     * @Author hmr
     * @Description 获得订单数量趋势图
     * @Date: 2020/3/2 16:53
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.dto.OrderTrendDto>
     **/
    @GetMapping("/trend/total/{type}")
    Message<OrderTrendDto> getOrderTotalTrend(@PathVariable("type") Integer type);

    /**
     * @Author hmr
     * @Description 根据评价id删除商品评价
     * @Date: 2020/5/11 19:54
     * @param targetId
     * @reture: void
     **/
    @DeleteMapping("/{targetId}")
    void deleteProductEvaluate(@PathVariable("targetId") Long targetId);

    /**
     * @Author hmr
     * @Description 根据评价id查看评价人
     * @Date: 2020/5/11 19:57
     * @param targetId
     * @reture: java.lang.Long
     **/
    @GetMapping("/evaluate/{id}")
    Long getEvaluateWriterIdById(@PathVariable("id") Long targetId);
}
