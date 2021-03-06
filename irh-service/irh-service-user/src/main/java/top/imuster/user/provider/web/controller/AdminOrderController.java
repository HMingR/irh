package top.imuster.user.provider.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.core.controller.BaseController;

/**
 * @ClassName: AdminOrderController
 * @Description: 管理员操作订单的控制器
 * @author: hmr
 * @date: 2020/1/9 11:13
 */
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController extends BaseController {

    /*@Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    @Autowired
    DonationApplyServiceFeignApi donationApplyServiceFeignApi;

    *//**
     * @Description: 管理员分页条件查询订单
     * @Author: hmr
     * @Date: 2020/1/11 10:41
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **//*
    @PostMapping
    @ApiOperation(value = "管理员分页条件查询订单", httpMethod = "POST")
    public Message<Page<OrderInfo>> orderList(@ApiParam @RequestBody Page<OrderInfo> page){
        Message<Page<OrderInfo>> pageMessage = orderServiceFeignApi.orderList(page);
        if(null == pageMessage){
            log.error("分页条件查询订单失败");
            return Message.createByError("查询失败");
        }
        return pageMessage;
    }

    @ApiOperation(value = "根据id查询订单", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<OrderInfo> getOrderById(@ApiParam("订单id") @PathVariable("id") Long id){
        OrderInfo order = orderServiceFeignApi.getOrderById(id);
        return Message.createBySuccess(order);
    }

    @ApiOperation("订单总金额趋势图")
    @GetMapping("/amount/trend/{type}")
    public Message<OrderTrendDto> orderAmountTrend(@PathVariable("type") Integer type){
        return orderServiceFeignApi.getOrderAmountTrend(type);
    }

    @ApiOperation("订单数量趋势图")
    @GetMapping("/total/trend/{type}")
    public Message<OrderTrendDto> orderTotalTrend(@PathVariable("type") Integer type){
        return orderServiceFeignApi.getOrderTotalTrend(type);
    }

    @PostMapping("/donation/apply")
    public Message<String> applyDonation(@RequestBody ProductDonationApplyInfo applyInfo ){
        return donationApplyServiceFeignApi.apply(applyInfo);
    }

    @PostMapping("/donation/approve")
    public Message<String> approveDonation(@RequestBody ProductDonationApplyInfo approveInfo){
        return donationApplyServiceFeignApi.approve(approveInfo);
    }

    @PostMapping("/donation/grant/{id}")
    Message<String> grant(@PathVariable("id") Long applyId) throws JsonProcessingException{
        return donationApplyServiceFeignApi.grantMoney(applyId, getCurrentUserIdFromCookie());
    }

    @GetMapping("/donation/determine/{applyId}")
    Message<String> determine(@PathVariable("applyId") Long applyId) throws IOException {
        return donationApplyServiceFeignApi.determineGrant(applyId, getCurrentUserIdFromCookie());
    }

    @PostMapping("/list")
    public Message<Page<ProductDonationApplyInfo>> getList(@RequestBody Page<ProductDonationApplyInfo> page){
        return donationApplyServiceFeignApi.getApplyList(page);
    }*/
}

