package top.imuster.order.provider.web.controller;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.PayService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @ClassName: AlipayController
 * @Description: 支付宝控制器
 * @author: hmr
 * @date: 2019/12/28 9:43
 */
@RestController
@RequestMapping("/pay")
@Api("支付宝控制器")
public class PayController extends BaseController {

    @Resource
    PayService payService;


    /**
     * @Description: 提交订单准备预下单,返回一个支付宝网站,需要解析里面的地址生成二维码
     * @Author: hmr
     * @Date: 2019/12/23 12:05
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @NeedLogin
    @ApiOperation("提交订单准备预下单,返回一个支付宝网站,需要解析里面的地址生成二维码")
    @GetMapping("/zfb/perPayment/{orderCode}")
    public Message<String> zfbPrePayment(@PathVariable("orderCode") String orderCode) throws OrderException {
        try{
            AlipayTradePrecreateResponse alipayResponse = payService.alipayF2F(orderCode);
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
    @PostMapping("/zfb/alipayNotify")
    public Message<String> payResult(HttpServletRequest request) throws ParseException {
        payService.aliCallBack(request);
        return Message.createBySuccess("支付成功,已提醒卖家");
    }

    @GetMapping("/wx/{orderCode}")
    public Message<String> wxPay(@PathVariable("orderCode") String orderCode) throws JsonProcessingException {
        return payService.wxPay(orderCode);
    }


}
