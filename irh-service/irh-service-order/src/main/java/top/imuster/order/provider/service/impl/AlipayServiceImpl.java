package top.imuster.order.provider.service.impl;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.config.AlipayConfig;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.AlipayService;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AlipayServiceImpl
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/22 20:24
 */
@Service("alipayService")
@Slf4j
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    AlipayConfig alipayConfig;

    @Resource
    OrderInfoService orderInfoService;

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        Configs.init("zfbinfo.properties");
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }


    @Override
    public AlipayTradePrecreateResponse alipayF2F(OrderInfo orderInfo) throws OrderException {
        //唯一的订单编号
        String orderCode = orderInfo.getOrderCode();
        //订单标题
        String orderRemark = orderInfo.getOrderRemark();
        String paymentMoney = orderInfo.getPaymentMoney();

        //这个字段表示卖家支付宝id，为空则表示自动进入平台的支付宝账号中
        String sellerId = "";

        String body = new StringBuilder().append("购买了").append(orderRemark).append("共计消费:").append(paymentMoney).append("元").toString();

        //卖家id，可以用来做统计
        String operatorId = String.valueOf(orderInfo.getSalerId());

        //支付超时时间
        String timeoutExpress = alipayConfig.getTimeoutExpress();

        //商品详情
        List<GoodsDetail> productInfos = new ArrayList<>();
        // todo 通过Feign向goods模块发送请求,查询商品的详情

        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(orderRemark).setTotalAmount(paymentMoney).setOutTradeNo(orderCode)
                .setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(operatorId)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("http://localhost:8082/alipay/payResult")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(productInfos);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                //dumpResponse(response);
                // 需要修改为运行机器上的路径
                //String filePath = String.format("/Users/sudo/Desktop/qr-%s.png", response.getOutTradeNo());
                //log.info("filePath:" + filePath);
                //需要使用file模块的微服务
                return response;
            case FAILED:
                log.error("支付宝预下单失败!!!");
                throw new OrderException("支付宝预下单失败");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                throw new OrderException("系统异常，预下单状态未知");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                throw new OrderException("不支持的交易状态，交易返回异常");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void aliCallBack(Map<String, String> params) throws OrderException {
        OrderInfo orderInfo = validateParams(params);
        orderInfo.setState(40);
        orderInfo.setTradeType(10);

        //更新订单状态
        orderInfoService.updateByKey(orderInfo);

    }

    /**
     * @Description: 校验支付宝回调的参数
     * @Author: hmr
     * @Date: 2019/12/23 21:45
     * @param paras
     * @reture: java.lang.String 返回订单编号
     **/
    private OrderInfo validateParams(Map<String, String> paras) throws OrderException {
        if(!Configs.getAppid().equals(paras.get("app_id"))){
            log.error("支付宝回调校验失败,appid被篡改");
            throw new OrderException("支付宝回调校验失败,appid被篡改");
        }
        String out_trade_no = paras.get("out_trade_no");
        OrderInfo realOrder = orderInfoService.getOrderInfoByOrderCode(out_trade_no);
        if(realOrder == null){
            log.error("支付宝回调校验失败,订单编号被篡改");
            throw new OrderException("支付宝回调校验失败,订单编号被篡改");
        }
        if(StringUtils.isNotEmpty(paras.get("total_amount")) && !realOrder.getPaymentMoney().equals(paras.get("total_amount"))){
            log.error("支付宝回调校验失败,支付金额被篡改");
            throw new OrderException("支付宝回调校验失败,支付金额被篡改");
        }
        String gmt_payment = paras.get("gmt_payment");
        if(StringUtils.isNotEmpty(gmt_payment)){
            //realOrder.setPaymentTime();
        }
        return realOrder;
    }
}
