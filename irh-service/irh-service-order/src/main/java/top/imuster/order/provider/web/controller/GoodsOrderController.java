package top.imuster.order.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.utils.UuidUtils;
import top.imuster.order.api.dto.ProductOrderDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;
import java.util.List;

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
     * @param productOrderDto
     * @param request
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("生成订单")
    @PutMapping("/create")
    @NeedLogin
    public Message<OrderInfo> createOrder(@RequestBody ProductOrderDto productOrderDto) throws Exception {
        Long userId = getCurrentUserIdFromCookie();
        OrderInfo order = orderInfoService.getOrderByProduct(productOrderDto, userId);
        return Message.createBySuccess(order);
    }

    @ApiOperation("进入生成订单页面的时候返回一个订单号,用来防止用户重复提交订单,前端提交时必须带上")
    @GetMapping("/code")
    public Message<String> generateOrderCode(){
        return Message.createBySuccess(String.valueOf(UuidUtils.nextId()));
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
        Long userId = getCurrentUserIdFromCookie();
        return orderInfoService.list(pageSize, currentPage, userId, type);
    }

    /**
     * @Description: 根据id删除订单
     * @Author: hmr
     * @Date: 2019/12/28 14:42
     * @param orderId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据id删除订单")
    @DeleteMapping("/{orderId}")
    @NeedLogin
    public Message editOrder(@PathVariable("orderId") Long orderId){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setState(30);
        orderInfoService.updateByKey(orderInfo);
        return Message.createBySuccess();
    }

    @ApiOperation(value = "根据id获得订单详情", httpMethod = "GET")
    @GetMapping("/{id}")
    @NeedLogin
    public Message<OrderInfo> getOrderDetailById(@PathVariable("id") Long id){
        List<OrderInfo> orderInfoList = orderInfoService.selectEntryList(id);
        if(orderInfoList == null || orderInfoList.isEmpty()) return Message.createBySuccess("未找到相关订单,刷新后重试");
        OrderInfo info = orderInfoList.get(0);
        return Message.createBySuccess(info);
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
}
