package top.imuster.order.provider.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.rabbitMq.SendOrderEvaluateDto;
import top.imuster.common.core.dto.rabbitMq.SendOrderExpireDto;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: OrderExpireQueueListener
 * @Description: OrderExpireQueueListener
 * @author: hmr
 * @date: 2020/6/14 19:50
 */
@Component
public class OrderExpireQueueListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Resource
    OrderInfoService orderInfoService;

    @Autowired
    RedisTemplate redisTemplate;

    @RabbitListener(queues = "queue_dlx_order_expire")
    private void listener(String msg){
        try{
            SendOrderExpireDto sendMessageDto = objectMapper.readValue(msg, SendOrderExpireDto.class);
            String redisKey  = RedisUtil.getOrderExpireKeyByOrderId(sendMessageDto.getOrderId());
            Boolean hasKey = redisTemplate.hasKey(redisKey);
            if(!hasKey) return;
            orderInfoService.cancelOrder(sendMessageDto.getOrderId(), sendMessageDto.getUserId(),  4);
        }catch (Exception e){
            log.error("关闭订单失败,消息为{},错误信息为{}", msg, e.getMessage(), e);
        }
    }


    @RabbitListener(queues = "queue_dlx_order_evaluate")
    private void finishOrderListener(String msg){
        try{
            SendOrderEvaluateDto sendOrderEvaluateDto = objectMapper.readValue(msg, SendOrderEvaluateDto.class);
            Long orderId = sendOrderEvaluateDto.getOrderId();
            Long userId = sendOrderEvaluateDto.getUserId();
            Boolean flag = orderInfoService.autoFinishOrder(orderId, userId);
        }catch (Exception e){
            log.error("自动完成订单失败,消息为{},错误信息为{}", msg, e.getMessage(), e);
        }
    }
}
