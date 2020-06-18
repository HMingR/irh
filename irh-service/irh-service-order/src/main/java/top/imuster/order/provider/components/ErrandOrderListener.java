package top.imuster.order.provider.components;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.rabbitMq.SendEmailDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.api.pojo.ErrandOrderInfo;
import top.imuster.order.provider.service.ErrandOrderService;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: OrderListener
 * @Description: 监听ERRAND队列的信息
 * @author: hmr
 * @date: 2020/2/12 10:48
 */
@Component
public class ErrandOrderListener {

    private static final Logger log = LoggerFactory.getLogger(ErrandOrderListener.class);

    @Resource
    ErrandOrderService errandOrderService;

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @RabbitListener(queues = "queue_inform_errand")
    public void generateOrder(String msg){
        try{
            ErrandOrderInfo order = new ObjectMapper().readValue(msg, ErrandOrderInfo.class);
            Long errandId = order.getErrandId();
            Integer errandVersion = order.getErrandVersion();
            boolean available = goodsServiceFeignApi.errandIsAvailable(errandId, errandVersion);
            String orderCode = order.getOrderCode();
            String redisKey = RedisUtil.getErrandOrderAvaliableMapKey(errandId);
            if(!available){
                redisTemplate.opsForHash().put(redisKey, orderCode, false);
                //当需要将订单存入数据库的时候，如果当前跑腿已经被人抢走了，则将结果存放在redis中，并结束当前方法
                //redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, orderCode, 1);
                return;
            }
            //下单成功也将订单编号放入redis中
//            redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, String.valueOf(orderCode), 2);

            boolean flag = goodsServiceFeignApi.updateErrandInfoById(errandId, errandVersion, 3);
            if(flag){
                order.setState(3);
                ErrandOrderInfo resultOrder = errandOrderService.acceptErrand(order);
                sendMessage(resultOrder);
                //当更新成功之后将errandInfo的id存储到redis中，并设置过期时间
                redisTemplate.opsForHash().put(redisKey, orderCode, true);
                redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);

                //将已经接单的跑腿信息的id保存在redis中，用来过滤请求
                String errandKey = RedisUtil.getErrandKey(errandId);
                redisTemplate.opsForValue().set(errandKey, 1, 5, TimeUnit.MINUTES);
                //redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, RedisUtil.getErrandKey(errandId), errandId);
                //redisTemplate.expire(RedisUtil.getErrandKey(errandId), 1, TimeUnit.MINUTES);
            }else{
                redisTemplate.opsForHash().put(redisKey, orderCode, false);
            }
        }catch (Exception e){
            log.error("解析从消息队列中得到的信息失败,错误信息为{},消息内容为{}",e.getMessage(), msg);
        }
    }


    private void sendMessage(ErrandOrderInfo orderInfo){
        SendUserCenterDto userCenterDto = new SendUserCenterDto();
        userCenterDto.setTargetId(orderInfo.getId());
        userCenterDto.setNewsType(30);
        userCenterDto.setToId(orderInfo.getHolderId());
        userCenterDto.setFromId(0L);
        userCenterDto.setContent(new StringBuilder("您已成功获得订单编号为: ").append(orderInfo.getOrderCode()).append(" 跑腿订单,请尽快按照要求完成任务,并且请当面结算酬金,点击查看详细信息").toString());
        generateSendMessageService.sendToMq(userCenterDto);
        SendEmailDto sendEmailDto = new SendEmailDto();
        String holderEmail = userServiceFeignApi.getUserEmailById(orderInfo.getHolderId());
        sendEmailDto.setEmail(holderEmail);
        sendEmailDto.setContent(new StringBuilder("irh:您已成功获得订单编号为: ").append(orderInfo.getOrderCode()).append("的跑腿订单,请尽快按照要求完成任务,或登录irh平台查看详情").toString());
        sendEmailDto.setTemplateEnum(TemplateEnum.SIMPLE_TEMPLATE);
        generateSendMessageService.sendToMq(sendEmailDto);
        userCenterDto.setContent("您发布的跑腿任务已经被接单,请耐心等待,点击查看详情");
        userCenterDto.setFromId(0L);
        userCenterDto.setToId(orderInfo.getPublisherId());
        userCenterDto.setTargetId(orderInfo.getId());
        userCenterDto.setNewsType(30);
        generateSendMessageService.sendToMq(userCenterDto);
    }

}
