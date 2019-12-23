package top.imuster.order.provider.service;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.exception.OrderException;

import java.util.Map;

/**
 * @ClassName: AlipayService
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/22 20:23
 */
public interface AlipayService {

    /**
     * @Description: 使用支付宝的当面付进行支付,返回二维码文件对象
     * @Author: hmr
     * @Date: 2019/12/23 10:41
     * @param orderInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    AlipayTradePrecreateResponse alipayF2F(OrderInfo orderInfo) throws OrderException;

    /**
     * @Description: 支付宝的回调验证和付款后的验证等逻辑
     * @Author: hmr
     * @Date: 2019/12/23 21:21
     * @param params
     * @reture: void
     **/
    void aliCallBack(Map<String, String>  params) throws OrderException;
}
