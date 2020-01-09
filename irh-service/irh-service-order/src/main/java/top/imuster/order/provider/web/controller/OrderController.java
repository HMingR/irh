package top.imuster.order.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.api.dto.ProductOrderDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: OrderController
 * @Description: 订单控制器
 * @author: lpf
 * @date: 2019/12/18 18:03
 */
@RestController
@RequestMapping("/order")
@Api("订单控制器")
public class OrderController extends BaseController{

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
    @PostMapping("/generateOrder")
    public Message generateOrder(@RequestBody ProductOrderDto productOrderDto, HttpServletRequest request){
        try{
            String token = StringUtils.substringAfter(request.getHeader(GlobalConstant.JWT_TOKEN_HEADER), GlobalConstant.JWT_TOKEN_HEAD);
            OrderInfo order = orderInfoService.getOrderByProduct(productOrderDto, token);
            return Message.createBySuccess(order);
        }catch (Exception e){
            throw new OrderException(e.getMessage());
        }
    }

    /**
     * @Description: 条件查询会员自己的订单
     * @Author: hmr
     * @Date: 2019/12/28 14:33
     * @param page
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping
    public Message orderList(@RequestBody @Validated(value = ValidateGroup.queryGroup.class)Page<OrderInfo> page, BindingResult bindingResult){
        validData(bindingResult);
        try{
            Page<OrderInfo> orderInfoPage = orderInfoService.selectPage(page.getSearchCondition(), page);
            return Message.createBySuccess(orderInfoPage);
        }catch (Exception e){
            logger.error("条件查询会员自己的订单失败",e.getMessage(), e);
            throw new OrderException(e.getMessage());
        }
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
    public Message editOrder(@PathVariable("orderId") Long orderId){
        try{
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(orderId);
            orderInfo.setState(30);
            int i = orderInfoService.updateByKey(orderInfo);
            if(i == 0){
                return Message.createByError("删除订单失败,没有找到当前订单,请刷新后重试");
            }
            return Message.createBySuccess("删除订单成功");
        }catch (Exception e){
            logger.error("删除会员订单失败",e.getMessage(), e);
            throw new OrderException(e.getMessage());
        }
    }

}
