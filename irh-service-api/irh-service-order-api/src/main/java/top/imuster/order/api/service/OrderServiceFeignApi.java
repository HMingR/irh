package top.imuster.order.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.OrderInfo;

/**
 * @ClassName: OrderServiceFeignApi
 * @Description: order模块提供给其他模块的接口
 * @author: hmr
 * @date: 2019/12/27 15:28
 */

@FeignClient(value = "order-service", path = "/feign/order")
public interface OrderServiceFeignApi {

    /**
     * @Description: 根据会员的id条件查询该会员的订单信息
     * @Author: hmr
     * @Date: 2019/12/27 15:31
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/list")
    Message list(@RequestBody Page<OrderInfo> page);
}
