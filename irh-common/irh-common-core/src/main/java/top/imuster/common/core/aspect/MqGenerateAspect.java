package top.imuster.common.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.config.RabbitMqConfig;
import top.imuster.common.core.dto.SendMessageDto;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MqGenerateAspect
 * @Description: MqGenerate拦截器
 * @author: hmr
 * @date: 2020/1/13 18:50
 */
@Component
@Aspect
@Slf4j
public class MqGenerateAspect {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Pointcut("@annotation(top.imuster.common.core.annotation.MqGenerate)")
    public void mqGenerateAnnotation(){}

    @After("mqGenerateAnnotation()")
    public void after(JoinPoint joinPoint){
        log.info("进入到MqGenerateAspect中");
        MqGenerate annotation = getAnnotation(joinPoint);
        Object[] args = joinPoint.getArgs();
        try{
            SendMessageDto sendMessageDto = new SendMessageDto();
            for(Object o : args){
                if(o instanceof SendMessageDto){
                    sendMessageDto = (SendMessageDto) o;
                    break;
                }
            }
            String body = new ObjectMapper().writeValueAsString(sendMessageDto);
            //接收该消息的队列
            String queueType = sendMessageDto.getType().toLowerCase().contains(RabbitMqConfig.QUEUE_INFORM_SMS)?RabbitMqConfig.QUEUE_INFORM_SMS:RabbitMqConfig.QUEUE_INFORM_EMAIL;
            Long expiration = sendMessageDto.getExpiration();
            TimeUnit unit = sendMessageDto.getUnit();

            log.info("向{}消息队列中发送消息,消息体为{}",queueType, body);
            System.out.println(body);
            rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM, "info.1.email.1", body);
            if(annotation.isSaveToRedis()){
                log.info("向redis中存入值, key为{},value为{}", sendMessageDto.getRedisKey(), sendMessageDto.getValue());
                redisTemplate.opsForValue().set(sendMessageDto.getRedisKey(), sendMessageDto.getValue(), expiration, unit);
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private MqGenerate getAnnotation(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method.getAnnotation(MqGenerate.class);
    }
}
