package top.imuster.user.provider.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

import java.util.List;

/**
 * @ClassName: AdminOrderController
 * @Description: 管理员操作订单的控制器
 * @author: hmr
 * @date: 2020/1/9 11:13
 */
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController extends BaseController {

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    /**
     * @Description: 管理员分页条件查询订单
     * @Author: hmr
     * @Date: 2020/1/11 10:41
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping
    @ApiOperation(value = "管理员分页条件查询订单", httpMethod = "POST")
    public Message orderList(@ApiParam @RequestBody Page<OrderInfo> page){
        List<OrderInfo> orderInfos = orderServiceFeignApi.orderList(page);
        if(null == orderInfos){
            logger.error("分页条件查询订单失败");
            return Message.createByError("查询失败");
        }
        return Message.createBySuccess(orderInfos);
    }

    @ApiOperation(value = "根据id查询订单", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message getOrderById(@ApiParam("订单id") @PathVariable("id") Long id){
        OrderInfo order = orderServiceFeignApi.getOrderById(id);
        return Message.createBySuccess(order);
    }

}

