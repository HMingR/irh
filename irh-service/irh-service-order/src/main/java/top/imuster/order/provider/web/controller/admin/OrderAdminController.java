package top.imuster.order.provider.web.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.UserDto;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.provider.service.OrderInfoService;
import top.imuster.order.provider.service.ProductDonationApplyInfoService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: OrderAdminController
 * @Description: OrderAdminController
 * @author: hmr
 * @date: 2020/6/9 14:24
 */
@RestController
@RequestMapping("/admin")
public class OrderAdminController extends BaseController {
    @Resource
    OrderInfoService orderInfoService;

    @Resource
    ProductDonationApplyInfoService productDonationApplyInfoService;
    
    @GetMapping("/order/{orderId}")
    public OrderInfo getOrderById(@PathVariable("orderId") Long orderId) {
        List<OrderInfo> orderInfoList = orderInfoService.selectEntryList(orderId);
        if(orderInfoList == null || orderInfoList.isEmpty()) return null;
        return orderInfoList.get(0);
    }

    
    @PostMapping("/order")
    public Message<Page<OrderInfo>> orderList(@RequestBody Page<OrderInfo> page) {
        OrderInfo condition = page.getSearchCondition();
        Page<OrderInfo> orderInfoPage = orderInfoService.selectPage(condition, page);
        return Message.createBySuccess(orderInfoPage);
    }

    
    @GetMapping("/order/trend/amount/{type}")
    public Message<OrderTrendDto> getOrderAmountTrend(@PathVariable("type") Integer type) {
        return orderInfoService.getOrderAmountTrend(type);
    }

    
    @GetMapping("/order/trend/total/{type}")
    public Message<OrderTrendDto> getOrderTotalTrend(@PathVariable("type") Integer type) {
        return orderInfoService.getOrderTotalTrend(type);
    }

    @PostMapping("/donation")
    public Message<String> apply(@RequestBody ProductDonationApplyInfo applyInfo){
        UserDto userInfo = getCurrentUserFromCookie();
        return productDonationApplyInfoService.apply(userInfo, applyInfo);
    }

    @PostMapping("/donation/approve")
    public Message<String> approve(@RequestBody ProductDonationApplyInfo approveInfo) {
        return productDonationApplyInfoService.approve(approveInfo);
    }

    /**
     * @Author hmr
     * @Description 自动选择订单,并返回订单总金额
     * @Date: 2020/6/13 17:22
     * @param applyId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping("/donation/grant/{id}")
    public Message<String> grantMoney(@PathVariable("id") Long applyId) throws JsonProcessingException {
        return productDonationApplyInfoService.grant(applyId, getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description 手动选择订单提交
     * @Date: 2020/6/13 17:24
     * @param ids
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping("/donation/grant/manual")
    public Message<String> grantMoney(@RequestBody ProductDonationApplyInfo applyInfo){
        return productDonationApplyInfoService.determine(applyInfo, getCurrentUserIdFromCookie());
    }

    @GetMapping("/donation/determine/{operatorId}/{applyId}")
    public Message<String> determineGrant(@PathVariable("applyId") Long applyId, @PathVariable("operatorId") Long operatorId) throws IOException {
        return productDonationApplyInfoService.determine(applyId, operatorId);
    }

    
    @GetMapping("/donation/{state}/{targetId}")
    public Message<ProductDonationApplyInfo> getApplyInfoById(@PathVariable("state") Integer state, @PathVariable("targetId") Long targetId) {
        return productDonationApplyInfoService.getApplyInfoById(state, targetId);
    }

    @PostMapping("/donation/apply/list")
    public Message<Page<ProductDonationApplyInfo>> getApplyList(@RequestBody Page<ProductDonationApplyInfo> page) {
        return productDonationApplyInfoService.getApplyList(page);
    }

    @PostMapping("/donation/approve/list")
    public Message<Page<ProductDonationApplyInfo>> getApproveList(@RequestBody Page<ProductDonationApplyInfo> page) {
        return productDonationApplyInfoService.getApproveList(page);
    }

    @PostMapping("/donation/order/list")
    public Message<Page<OrderInfo>> getDonationOrderList(@RequestBody Page<OrderInfo> page){
        return orderInfoService.getDonationOrderList(page);
    }

}
