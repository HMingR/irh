package top.imuster.order.provider.web.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.AlipayService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: AlipayController
 * @Description: 支付宝控制器
 * @author: hmr
 * @date: 2019/12/28 9:43
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController extends BaseController {

    @Resource
    AlipayService alipayService;

    /**
     * @Description: 提交订单准备预下单,返回一个支付宝网站,需要解析里面的地址生成二维码
     * @Author: hmr
     * @Date: 2019/12/23 12:05
     * @param orderInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    //@NeedLogin(validate = true)
    @ApiOperation("提交订单准备预下单,返回一个支付宝网站,需要解析里面的地址生成二维码")
    @PostMapping("/perPayment")
    public Message prePayment(@RequestBody @Validated(ValidateGroup.prePayment.class) OrderInfo orderInfo, BindingResult bindingResult, HttpServletRequest request) throws OrderException {
        validData(bindingResult);
        try{
            AlipayTradePrecreateResponse alipayResponse = alipayService.alipayF2F(orderInfo);
            return Message.createBySuccess(alipayResponse.getQrCode());
        }catch (Exception e){
            throw new OrderException(e.getMessage());
        }
    }

    /**
     * @Description: 支付宝回调地址
     * @Author: hmr
     * @Date: 2019/12/23 20:11
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/alipayNotify")
    public Message payResult(HttpServletRequest request){
        Map<String,String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
            String name = (String)iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for(int i = 0 ; i <values.length;i++){
                valueStr = (i == values.length -1)?valueStr + values[i]:valueStr + values[i]+",";
            }
            params.put(name,valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!alipayRSACheckedV2){
                return Message.createByError("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常",e);
        }
        alipayService.aliCallBack(params);
        return null;
    }


}
