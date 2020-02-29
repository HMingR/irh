package top.imuster.order.provider.web.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: OrderServuceFeignClient
 * @Description:
 * @author: hmr
 * @date: 2019/12/27 15:33
 */
@RestController
@RequestMapping("/feign/order")
@Slf4j
public class OrderServiceFeignClient implements OrderServiceFeignApi {

    @Resource
    OrderInfoService orderInfoService;

    @Override
    @GetMapping("/{orderId}")
    public OrderInfo getOrderById(@PathVariable("orderId") Long orderId) {
        OrderInfo orderInfo = orderInfoService.selectEntryList(orderId).get(0);
        return orderInfo;
    }

    @Override
    @PostMapping
    public Message<Page<OrderInfo>> orderList(@RequestBody Page<OrderInfo> page) {
        OrderInfo condition = page.getSearchCondition();
        Page<OrderInfo> orderInfoPage = orderInfoService.selectPage(condition, page);
        return Message.createBySuccess(orderInfoPage);
    }
}
