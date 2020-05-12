package top.imuster.order.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: OrderController
 * @Description: 二手商品订单控制器
 * @author: lpf
 * @date: 2019/12/18 18:03
 */
@RestController
@RequestMapping("/order/es")
@Api("订单控制器")
public class GoodsOrderController extends BaseController{

    @Resource
    OrderInfoService orderInfoService;

    /**
     * @Description: 生成订单
     * @Author: hmr
     * @Date: 2019/12/28 14:26
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("生成订单")
    @PutMapping("/create/{productId}")
    @NeedLogin
    public Message<OrderInfo> createOrder(@PathVariable("productId") Long productId, @RequestBody OrderInfo orderInfo) throws Exception {
        Long userId = getCurrentUserIdFromCookie();
        return orderInfoService.getOrderByProduct(orderInfo, userId, productId);
    }

    @ApiOperation("进入生成订单页面的时候返回一个订单号,用来防止用户重复提交订单,前端提交时必须带上")
    @NeedLogin
    @GetMapping("/code")
    public Message<String> generateOrderCode(){
        return orderInfoService.createOrderCode(getCurrentUserIdFromCookie());
    }


    /**
     * @Author hmr
     * @Description 条件查询会员自己的订单
     * @Date: 2020/5/10 20:31
     * @param type
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.order.api.pojo.OrderInfo>>
     **/
    @ApiOperation("条件分页查询会员自己的订单，按照时间降序排序")
    @GetMapping("/list/{type}/{pageSize}/{currentPage}")
    @NeedLogin
    public Message<Page<OrderInfo>> orderList(@PathVariable("type") Integer type, @PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        if(type != 1 && type != 2) return Message.createByError("参数异常,请刷新后重试");
        if(pageSize < 1 || currentPage < 1) return Message.createByError("参数错误,请刷新");
        Long userId = getCurrentUserIdFromCookie();
        return orderInfoService.list(pageSize, currentPage, userId, type);
    }


    /**
     * @Author hmr
     * @Description type  1-作为卖家  2-作为买家
     * @Date: 2020/5/11 9:38
     * @param id
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.pojo.OrderInfo>
     **/
    @ApiOperation(value = "根据id获得订单详情", httpMethod = "GET")
    @GetMapping("/{type}/{id}")
    @NeedLogin
    public Message<OrderInfo> getOrderDetailById(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        if(type != 1 && type != 2) return Message.createByError("参数错误");
        return orderInfoService.getOrderDetailById(id, type,getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description 买家完成订单
     * @Date: 2020/4/29 16:35
     * @param orderId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/finish/{orderId}")
    @NeedLogin
    public Message<String> finish(@PathVariable("orderId") Long orderId){
        return orderInfoService.finishOrder(orderId, getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/12 11:09
     * @param type   1-买家删除   2-卖家删除
     * @param orderId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @NeedLogin
    @DeleteMapping("/cancel/{type}/{id}")
    public Message<String> cancleOrder(@PathVariable("type") Integer type, @PathVariable("id") Long orderId){
        return orderInfoService.cancleOrder(orderId, getCurrentUserIdFromCookie(), type);
    }
}
