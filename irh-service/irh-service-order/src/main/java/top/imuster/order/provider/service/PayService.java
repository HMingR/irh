package top.imuster.order.provider.service;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.provider.exception.OrderException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @ClassName: PayService
 * @Description:
 * @author: hmr
 * @date: 2019/12/22 20:23
 */
public interface PayService {

    /**
     * @Description: 使用支付宝的当面付进行支付,返回二维码文件对象
     * @Author: hmr
     * @Date: 2019/12/23 10:41
     * @param orderInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    AlipayTradePrecreateResponse alipayF2F(String orderInfo) throws OrderException;

    /**
     * @Description: 支付宝的回调验证和付款后的验证等逻辑
     * @Author: hmr
     * @Date: 2019/12/23 21:21
     * @param params
     * @reture: void
     **/
    void aliCallBack(HttpServletRequest request) throws OrderException, ParseException;

    /**
     * @Author hmr
     * @Description 微信支付
     * @Date: 2020/6/4 11:18
     * @param orderCode
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> wxPay(String orderCode) throws JsonProcessingException;

    /**
     * @Author hmr
     * @Description 跑腿支付
     * @Date: 2020/6/18 11:08
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> errandPay(Long id);

    /**
     * 测试
     * @return
     */
    Message<String> testLcn();
}
