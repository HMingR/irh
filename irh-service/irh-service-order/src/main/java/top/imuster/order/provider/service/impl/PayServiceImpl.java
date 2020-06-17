package top.imuster.order.provider.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendEmailDto;
import top.imuster.common.core.dto.rabbitMq.SendOrderEvaluateDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.PayService;
import top.imuster.order.provider.service.OrderInfoService;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: PayServiceImpl
 * @Description:
 * @author: hmr
 * @date: 2019/12/22 20:24
 */
@Service("payService")
public class PayServiceImpl implements PayService {

    private static final Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Autowired
    ObjectMapper objectMapper;

    @Resource
    OrderInfoService orderInfoService;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Autowired
    RedisTemplate redisTemplate;

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
                .setFormat("json").build();    }


    @Override
    public AlipayTradePrecreateResponse alipayF2F(String orderCode) throws OrderException {
        Long productId = (Long) redisTemplate.opsForValue().get(RedisUtil.getOrderCodeExpireKey(orderCode));
        if(productId == null) throw new OrderException("订单不存在或订单超时,请重新生成订单");
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderCode(orderCode);

        //订单标题
        String subject = "irh校园智慧服务平台";
        String paymentMoney = orderInfo.getPaymentMoney();

        //这个字段表示卖家支付宝id，为空则表示自动进入平台的支付宝账号中
        String sellerId = "";
        String body = new StringBuilder().append(subject).append("共计消费:").append(paymentMoney).append("元").toString();

        //卖家id，可以用来做统计
        String operatorId = String.valueOf(orderInfo.getSalerId());

        //支付超时时间
        String timeoutExpress = "30m";

        //商品详情
        List<GoodsDetail> productInfos = new ArrayList<>();
        ProductInfo productInfo = goodsServiceFeignApi.getProductBriefInfoById(productId);
        GoodsDetail goodsDetail = getGoodsDetail(productInfo);
        productInfos.add(goodsDetail);


        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(paymentMoney).setOutTradeNo(orderCode)
                .setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(operatorId)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("https://222.186.174.9:13163/api/order/alipay/alipayNotify")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
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


    private GoodsDetail getGoodsDetail(ProductInfo productInfo){
        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsName(productInfo.getTitle());
        goodsDetail.setGoodsId(String.valueOf(productInfo.getId()));
        goodsDetail.setPrice(Long.parseLong(productInfo.getSalePrice()));
        return goodsDetail;
    }

    @SneakyThrows
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void aliCallBack(HttpServletRequest request) throws OrderException, ParseException {
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
        log.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!alipayRSACheckedV2){
                log.error("支付宝回调地址收到非法请求");
                return;
            }
        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常",e);
        }

        OrderInfo orderInfo = validateParams(params);
        orderInfo.setState(45);
        orderInfo.setTradeType(10);

        Integer state = orderInfoService.completeTrade(orderInfo);
        if(state != 1){
            log.error("-------->支付宝支付成功回调时修改订单状态失败,订单信息为{}", objectMapper.writeValueAsString(orderInfo));
        }

        //更新商品库存状态
        boolean b = goodsServiceFeignApi.updateProductState(orderInfo.getProductId(), 4);
        if(!b) log.error("---------->支付成功之后改变商品状态失败,订单信息为{}", objectMapper.writeValueAsString(orderInfo));

        //支付成功之后将订单保存在redis中
        String orderExpireKeyByOrderId = RedisUtil.getOrderExpireKeyByOrderId(orderInfo.getId());
        redisTemplate.opsForValue().set(orderExpireKeyByOrderId, 1, 20, TimeUnit.MINUTES);
        sendMessage(orderInfo);
    }

    @Override
    public Message<String> wxPay(String orderCode) throws JsonProcessingException {
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderCode(orderCode);
        if(orderInfo == null) return Message.createByError("未找到相关订单");
        orderInfo.setState(45);
        orderInfo.setPaymentTime(DateUtil.current());
        Integer state = orderInfoService.completeTrade(orderInfo);
        if(state != 1){
            log.error("-------->微信支付成功回调时修改订单状态失败,订单信息为{}", objectMapper.writeValueAsString(orderInfo));
        }

        //更新商品库存状态
        boolean b = goodsServiceFeignApi.updateProductState(orderInfo.getProductId(), 4);
        if(!b) log.error("---------->支付成功之后改变商品状态失败,订单信息为{}", objectMapper.writeValueAsString(orderInfo));

        String orderExpireKeyByOrderId = RedisUtil.getOrderExpireKeyByOrderId(orderInfo.getId());
        redisTemplate.opsForValue().set(orderExpireKeyByOrderId, 1, 20, TimeUnit.MINUTES);

        sendMessage(orderInfo);

        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description 给卖家和买家发送消息
     * @Date: 2020/5/12 14:09
     * @param orderInfo
     * @reture: void
     **/
    private void sendMessage(OrderInfo orderInfo){
        Long salerId = orderInfo.getSalerId();
        String sendTo = userServiceFeignApi.getUserEmailById(salerId);

        //发给卖家邮件
        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setSubject("发货通知");
        sendEmailDto.setTemplateEnum(TemplateEnum.SIMPLE_TEMPLATE);
        sendEmailDto.setContent(new StringBuffer("您在irh平台发布的编号为: ").append(orderInfo.getProductId()).append(" 的商品已经被其他人买走了,请登录irh平台查看详情").toString());
        sendEmailDto.setDate(DateUtil.now());
        sendEmailDto.setEmail(sendTo);
        generateSendMessageService.sendToMq(sendEmailDto);

        SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
        sendUserCenterDto.setTargetId(orderInfo.getId());
        sendUserCenterDto.setToId(salerId);
        sendUserCenterDto.setNewsType(40);
        sendUserCenterDto.setContent(new StringBuffer("您在irh平台发布的编号为: ").append(orderInfo.getProductId()).append("商品已经被其他人买走了,点击查看").toString());
        sendUserCenterDto.setDate(DateUtil.now());
        sendUserCenterDto.setFromId(-1L);
        generateSendMessageService.sendToMq(sendUserCenterDto);

        sendUserCenterDto = new SendUserCenterDto();

        sendUserCenterDto.setTargetId(orderInfo.getId());
        sendUserCenterDto.setContent(new StringBuffer("您已成功下单,本次交易的订单号为: ").append(orderInfo.getOrderCode()).append(",点击查看").toString());
        sendUserCenterDto.setFromId(-1L);
        sendUserCenterDto.setNewsType(40);
        sendUserCenterDto.setToId(orderInfo.getBuyerId());
        sendUserCenterDto.setDate(DateUtil.now());
        generateSendMessageService.sendToMq(sendUserCenterDto);


        //发送自动完成订单的信息到mq
        SendOrderEvaluateDto sendOrderEvaluateDto = new SendOrderEvaluateDto();
        sendOrderEvaluateDto.setUserId(orderInfo.getBuyerId());
        sendOrderEvaluateDto.setOrderId(orderInfo.getId());
        generateSendMessageService.sendDeadMsg(sendOrderEvaluateDto);


    }

    /**
     * @Description: 校验支付宝回调的参数(必须校验支付宝的回调,以免支付宝的消息被替换)
     * @Author: hmr
     * @Date: 2019/12/23 21:45
     * @param paras
     * @reture: java.lang.String 返回订单编号
     **/
    private OrderInfo validateParams(Map<String, String> paras) throws OrderException, ParseException {
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
            realOrder.setPaymentTime(DateUtil.parse(gmt_payment));
        }else{
            realOrder.setPaymentTime(DateUtil.parse(new Date()));
        }
        return realOrder;
    }
}
