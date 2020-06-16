package top.imuster.common.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.core.config.RabbitMqConfig;
import top.imuster.common.core.dto.rabbitMq.*;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.exception.GlobalException;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: GenerateSendMessageService
 * @Description: 发送消息
 * @author: hmr
 * @date: 2020/1/17 10:26
 */
@Service("generateSendMessageService")
public class GenerateSendMessageService {

    private static final Logger log = LoggerFactory.getLogger(GenerateSendMessageService.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * @Author hmr
     * @Description 发送单个消息到mq
     * @Date: 2020/1/17 10:33
     * @param sendMessageDto
     * @reture: void
     **/
    public void sendToMq(Send2MQ sendMessageDto){
        log.info("发送单个消息，消息的RoutingKey为{}", sendMessageDto.getType().getRoutingKey());
        send2Mq(sendMessageDto);
    }


    /**
     * @Author hmr
     * @Description 发送注册邮件
     * @Date: 2020/3/1 16:21
     * @param sendEmailDto
     * @reture: void
     **/
    public void sendRegistEmail(SendEmailDto sendEmailDto){
        send2Mq(sendEmailDto);
        save2Redis(sendEmailDto.getRedisKey(), sendEmailDto.getContent(), sendEmailDto.getExpiration(), sendEmailDto.getUnit());
    }

    /**
     * @Author hmr
     * @Description 发送到消息队列中
     * @Date: 2020/3/1 16:21
     * @param sendMessage
     * @reture: void
     **/
    private void send2Mq(Send2MQ sendMessage){
        try {
            MqTypeEnum type = sendMessage.getType();
            if(type == null){
                log.error("发送的邮件类型为空,发送的信息为{}", sendMessage);
                throw new GlobalException("服务器内部异常");
            }
            String body = objectMapper.writeValueAsString(sendMessage);
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,  type.getRoutingKey(), body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author hmr
     * @Description 保存到reids中
     * @Date: 2020/3/1 16:16
     * @param redisKey redis中的key
     * @param content redis中的value
     * @param expir 过期时间
     * @param timeUnit 时间单位
     * @reture: void
     **/
    private void save2Redis(String redisKey, String content, Long expir, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(redisKey, content);
        redisTemplate.expire(redisKey, expir, timeUnit);
    }


    /**
     * @Author hmr
     * @Description 下单成功之后发送死信消息到MQ中，用来监听支付超时
     * @Date: 2020/6/16 9:28
     * @param msgEntry
     * @reture: void
     **/
    public void sendOrderDeadMsg(SendOrderExpireDto msgEntry){
        try{
            Long ttl = msgEntry.getTtl();
            rabbitTemplate.convertAndSend(RabbitMqConfig.DLX_EXCHANGE_INFORM, msgEntry.getType().getRoutingKey(), objectMapper.writeValueAsString(msgEntry), new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().getHeaders().put("expiration", ttl == null ? "600000" : ttl.toString());    //如果没有过期时间，则设置为10分钟
                    return message;
                }
            });
        }catch (Exception e){
            log.error("发送订单超时死信消息失败,订单id为{},错误消息为{}",msgEntry.getOrderId(), e.getMessage(), e);
        }
    }

    public void sendDeadMsg(SendDead2MQ msgEntry){
        Long ttl = msgEntry.getTtl();
        try{
            rabbitTemplate.convertAndSend(RabbitMqConfig.DLX_EXCHANGE_INFORM, msgEntry.getType().getRoutingKey(), objectMapper.writeValueAsString(msgEntry), new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().getHeaders().put("expiration", ttl == null ? "600000" : ttl.toString());    //如果没有过期时间，则设置为10分钟
                    return message;
                }
            });
        }catch (Exception e){
            log.error("发送死信消息失败,订单id为{},错误消息为{}",msgEntry.getOrderId(), e.getMessage(), e);
        }
    }

}
