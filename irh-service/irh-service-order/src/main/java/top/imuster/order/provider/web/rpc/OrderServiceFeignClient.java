package top.imuster.order.provider.web.rpc;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.pojo.ProductEvaluateInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.order.provider.service.OrderInfoService;
import top.imuster.order.provider.service.ProductEvaluateInfoService;

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
public class OrderServiceFeignClient implements OrderServiceFeignApi {

    @Resource
    OrderInfoService orderInfoService;

    @Resource
    ProductEvaluateInfoService productEvaluateInfoService;

    @Override
    @GetMapping("/{orderId}")
    public OrderInfo getOrderById(@PathVariable("orderId") Long orderId) {
        List<OrderInfo> orderInfoList = orderInfoService.selectEntryList(orderId);
        if(orderInfoList == null || orderInfoList.isEmpty()) return null;
        return orderInfoList.get(0);
    }

    @Override
    @PostMapping
    public Message<Page<OrderInfo>> orderList(@RequestBody Page<OrderInfo> page) {
        OrderInfo condition = page.getSearchCondition();
        Page<OrderInfo> orderInfoPage = orderInfoService.selectPage(condition, page);
        return Message.createBySuccess(orderInfoPage);
    }

    @Override
    @GetMapping("/trend/amount/{type}")
    public Message<OrderTrendDto> getOrderAmountTrend(@PathVariable("type") Integer type) {
        return orderInfoService.getOrderAmountTrend(type);
    }

    @Override
    @GetMapping("/trend/total/{type}")
    public Message<OrderTrendDto> getOrderTotalTrend(@PathVariable("type") Integer type) {
        return orderInfoService.getOrderTotalTrend(type);
    }

    @Override
    @DeleteMapping("/{targetId}")
    public void deleteProductEvaluate(@PathVariable("targetId") Long targetId) {
        ProductEvaluateInfo condition = new ProductEvaluateInfo();
        condition.setId(targetId);
        condition.setState(1);
        productEvaluateInfoService.updateByKey(condition);
    }

    @Override
    @GetMapping("/evaluate/{id}")
    public Long getEvaluateWriterIdById(@PathVariable("id") Long targetId) {
        return null;
    }
}
