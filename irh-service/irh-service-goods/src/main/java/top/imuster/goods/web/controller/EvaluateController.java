package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.base.utils.JwtTokenUtil;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductEvaluateInfoService;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: EvaluateController
 * @Description: 商品评价控制器
 * @author: hmr
 * @date: 2020/1/9 14:24
 */
@Api("商品评价,指的是购买商品之后对商品的评价")
@RestController
@RequestMapping("/goods/evaluate")
public class EvaluateController extends BaseController {

    @Resource
    ProductEvaluateInfoService productEvaluateInfoService;

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    /**
     * @Description: 用户的订单只有在收货状态的时候才能进行评价
     * @Author: hmr
     * @Date: 2020/1/9 11:51
     * @param productEvaluateInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据订单的id评论(用户的订单只有在收货状态的时候才能进行评论,前台可以先进行一次逻辑判断)")
    @PostMapping("/{orderId}")
    public Message evaluateByOrderId(@PathVariable("orderId") Long orderId,@RequestBody ProductEvaluateInfo productEvaluateInfo){
        try{
            OrderInfo order = orderServiceFeignApi.getOrderById(orderId);
            if(order == null){
                return Message.createByError("未找到指定的订单号");
            }
            if(order.getState() != 50){
                return Message.createByError("请先确定收货，完成该订单之后才能进行评价");
            }
            productEvaluateInfoService.evaluateByOrder(order, productEvaluateInfo);
            return Message.createBySuccess("评论成功");
        }catch (Exception e){
            logger.error("用户根据订单id评价商品失败", e.getMessage(), e);
            throw new GoodsException("用户根据订单id评价商品失败");
        }
    }


    @ApiOperation("根据评论id删除自己的评价,别人不能删除")
    @DeleteMapping("/{id}")
    public Message deleteEvaluateById(@PathVariable("id") Long id, HttpServletRequest request){
        try{
            //从请求头中得到token，然后解析得到其中的用户id
            String token = StringUtils.substringAfter(request.getHeader(GlobalConstant.JWT_TOKEN_HEADER), GlobalConstant.JWT_TOKEN_HEAD);
            Long userId = JwtTokenUtil.getUserIdFromToken(token);

            ProductEvaluateInfo productEvaluateInfo = new ProductEvaluateInfo();
            productEvaluateInfo.setId(id);
            ProductEvaluateInfo evaluateInfo = productEvaluateInfoService.selectEntryList(productEvaluateInfo).get(0);
            if(null == evaluateInfo){
                return Message.createByError("没有找到该评价,请刷新后重试");
            }
            Long buyerId = evaluateInfo.getBuyerId();
            if(buyerId != userId){
                return Message.createByError("用户只能删除自己的评价，不能删除别人的评价");
            }
            productEvaluateInfo.setState(1);
            int i = productEvaluateInfoService.updateByKey(productEvaluateInfo);
            if(i != 1){
                return Message.createByError("删除失败,请刷新后重试");
            }
            return Message.createBySuccess("删除成功");
        }catch (Exception e){
            logger.error("用户根据id删除评论失败",e.getMessage(), e);
            throw new GoodsException("用户根据id删除评论失败");
        }
    }

    /**
     * @Description: type -1表示查询买家的所有有效评价(用来查询自己评价记录)    -2表示卖家被评论的评论(查询别人对自己的评价)
     * @Author: hmr
     * @Date: 2020/1/9 15:10
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据用户的id查询对该用户的所有评价(1:查询自己 2:查询卖家被评论的记录)")
    @GetMapping("/{type}/{customerId}")
    public Message listById(@PathVariable("customerId") Long id, @PathVariable("type") Integer type){
        try{
            ProductEvaluateInfo productEvaluateInfo = new ProductEvaluateInfo();
            if(type == 1)
                productEvaluateInfo.setBuyerId(id);
            if(type == 2)
                productEvaluateInfo.setSalerId(id);

            productEvaluateInfo.setState(2);
            List<ProductEvaluateInfo> productEvaluateInfos = productEvaluateInfoService.selectEntryList(productEvaluateInfo);
            return Message.createBySuccess(productEvaluateInfos);
        }catch (Exception e){
            logger.error("用户评价失败",e.getMessage(), e);
            throw new GoodsException("用户查询评价失败");
        }
    }
}
