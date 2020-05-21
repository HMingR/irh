package top.imuster.order.provider.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;

/**
 * @ClassName: WxPayController
 * @Description: 微信支付
 * @author: hmr
 * @date: 2020/5/21 16:10
 */
@RestController
@RequestMapping("/wxpay")
public class WxPayController {

    @GetMapping("/pay/{orderCode}")
    public Message<String> pay(@PathVariable("orderCode") String orderCode){
        return null;
    }
}
