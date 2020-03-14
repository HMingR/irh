package top.imuster.order.provider.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.order.api.pojo.ErrandOrder;
import top.imuster.order.provider.service.ErrandOrderService;

import javax.annotation.Resource;

/**
 * @ClassName: ErrandOrderController
 * @Description: 跑腿订单controller
 * @author: hmr
 * @date: 2020/2/11 20:14
 */
@RestController
@RequestMapping("/order/errand")
@Api
public class ErrandOrderController extends BaseController {

    @Resource
    ErrandOrderService errandOrderService;

    /**
     * @Author hmr
     * @Description 通过消息队列来控制并发
     * @Date: 2020/2/12 10:04
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("接单，需要轮询操作")
    @NeedLogin
    @GetMapping("/{id}")
    public Message<String> orderReceive(@PathVariable("id") Long id) throws JsonProcessingException {
        String s = errandOrderService.receiveOrder(id, getCurrentUserIdFromCookie());
        return Message.createBySuccess(s);
    }

    @ApiOperation("根据id删除订单")
    @DeleteMapping("/{id}")
    public Message<String> delete(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        return errandOrderService.delete(id, userId);
    }

    @ApiOperation("查看订单，type(1-查看别人接收自己发布的跑腿  2-自己接的跑腿订单)")
    @PostMapping("/{type}")
    public Message<Page<ErrandOrder>> list(@RequestBody Page<ErrandOrder> page, @PathVariable("type") Integer type){
        ErrandOrder searchCondition = page.getSearchCondition();
        if(searchCondition == null){
            page.setSearchCondition(new ErrandOrder());
        }
        if(StringUtils.isBlank(searchCondition.getOrderField())){
            searchCondition.setOrderField("create_time");
            searchCondition.setOrderFieldType("DESC");
        }
        Long userId = getCurrentUserIdFromCookie();
        if(type == 1) searchCondition.setPublisherId(userId);
        if(type == 2) searchCondition.setHolderId(userId);
        page.setSearchCondition(searchCondition);
        Page<ErrandOrder> errandOrderPage = errandOrderService.selectPage(searchCondition, page);
        return Message.createBySuccess(errandOrderPage);
    }

    /**
     * @Author hmr
     * @Description 根据id查看订单详情
     * @Date: 2020/2/12 11:58
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.pojo.ErrandOrder>
     **/
    @ApiOperation("根据id查看订单详情")
    @GetMapping("/detail/{id}")
    public Message<ErrandOrder> getOrderById(@PathVariable("id") Long id){
        ErrandOrder errandOrder = errandOrderService.selectEntryList(id).get(0);
        return Message.createBySuccess(errandOrder);
    }

    /**
     * @Author hmr
     * @Description 用户接单之后需要一直轮询该接口，查看接单状态
     * @Date: 2020/2/12 11:56
     * @param code 为接单返回的订单编号
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("用户接单之后需要一直轮询该接口,code为接单返回的订单编号")
    @GetMapping("/state/{code}")
    public Message<String> getOrderStateByCode(@PathVariable("code") String code){
        return errandOrderService.getOrderStateByCode(code);
    }

    /**
     * @Author hmr
     * @Description 发布者完成订单
     * @Date: 2020/2/12 12:08
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation(value = "发布者完成订单", httpMethod = "GET")
    @NeedLogin
    @GetMapping("/finish/{id}")
    public Message<String> finishOrder(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        ErrandOrder errandOrder = errandOrderService.selectEntryList(id).get(0);
        if(!errandOrder.getPublisherId().equals(userId)){
            logger.error("订单的发布者和当前用户不一致,订单id为{},当前用户id为{}",id, userId);
            return Message.createByError("非法操作,恶意篡改登录信息,如非恶意请重新登录");
        }
        errandOrder.setState(4);
        errandOrderService.updateByKey(errandOrder);
        return Message.createBySuccess();
    }
}
