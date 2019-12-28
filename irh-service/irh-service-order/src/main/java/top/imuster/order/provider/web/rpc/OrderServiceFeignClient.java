package top.imuster.order.provider.web.rpc;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: OrderServuceFeignClient
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/27 15:33
 */
@RestController
@RequestMapping("/")
public class OrderServiceFeignClient implements OrderServiceFeignApi {

    @Resource
    OrderInfoService orderInfoService;

    @Override
    @PostMapping("/feign/order/list")
    public Message list(Page<OrderInfo> page) throws OrderException {
        OrderInfo condition =  page.getSearchCondition();
        return Message.createBySuccess(orderInfoService.selectPage(condition, page));
    }
}
