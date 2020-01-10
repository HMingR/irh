package top.imuster.order.api.service.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

/**
 * @ClassName: OrderServiceFeignApiHystrix
 * @Description: 服务降级
 * @author: hmr
 * @date: 2019/12/29 22:02
 */
@Component
@Slf4j
public class OrderServiceFeignApiHystrix implements OrderServiceFeignApi {

    @Override
    public OrderInfo getOrderById(Long orderId) {
        log.error("OrderServiceFeignApiHystrix--> 根据订单的id条件查询订单信息服务失败");
        return null;
    }
}
