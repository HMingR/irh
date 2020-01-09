package top.imuster.order.api.service.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

/**
 * @ClassName: OrderServiceFeignApiHystrix
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/29 22:02
 */
@Component
@Slf4j
public class OrderServiceFeignApiHystrix implements OrderServiceFeignApi {
    @Override
    public Message list(Page<OrderInfo> page) {
        log.error("OrderServiceFeignApiHystrix--> 根据会员的id条件查询该会员的订单信息服务失败");
        return Message.createByError("当前服务暂时无法访问，请稍后重试");
    }

    @Override
    public OrderInfo getOrderById(Long orderId) {
        log.error("OrderServiceFeignApiHystrix--> 根据订单的id条件查询订单信息服务失败");
        return null;
    }
}
