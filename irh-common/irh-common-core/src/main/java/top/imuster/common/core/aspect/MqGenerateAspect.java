package top.imuster.common.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
import top.imuster.common.core.exception.GlobalException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

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

    private Method targetMethod;

    private MqGenerate annotation;

    @Pointcut("@annotation(top.imuster.common.core.annotation.MqGenerate)")
    public void mqGenerateAnnotation(){}

    /**
     * @Author hmr
     * @Description 必须是around，不能是after，因为在这个方法里面需要抛出异常，如果提前执行了，全局异常处理类就捕捉不到抛出的异常了
     * @Date: 2020/1/15 18:26
     * @param joinPoint
     * @reture: java.lang.Object
     **/
    @Around("mqGenerateAnnotation()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        log.info("进入到MqGenerateAspect中");
        annotation = getAnnotation(joinPoint);
        Object[] args = joinPoint.getArgs();
        log.info("获得目标方法的形参{}", args);
        //判断是不是多个
        if(!annotation.one()){
            ArrayList<SendMessageDto> sendMessageDtos = new ArrayList<>();
            Parameter[] parameters = targetMethod.getParameters();
            int index = -1;
            for(Parameter parameter : parameters){
                index++;
                Class<?> type = parameter.getType();
                if(type.isPrimitive()) continue;

                ParameterizedType pt =(ParameterizedType)  parameter.getParameterizedType();
                Class actualTypeArgument = (Class)pt.getActualTypeArguments()[0];
                if(actualTypeArgument.isAssignableFrom(SendMessageDto.class)){
                    sendMessageDtos = (ArrayList) args[index];
                    log.info("成功获得需要发送的信息{},index为{}", sendMessageDtos, index);
                    break;
                }
            }
            if(null == sendMessageDtos){
                log.error("没有从方法中获得泛型为SendMessageDto的list");
                throw new GlobalException("服务器内部异常");
            }
            sendMany(sendMessageDtos);
        }else{
            SendMessageDto sendMessageDto = new SendMessageDto();
            for(Object o : args){
                if(o instanceof SendMessageDto){
                    sendMessageDto = (SendMessageDto) o;
                    break;
                }
            }
            send(sendMessageDto);
        }
        return result;
    }

    /**
     * @Author hmr
     * @Description 发送多个消息
     * @Date: 2020/1/15 16:39
     * @param sendMessageDtos
     * @reture: void
     **/
    public void sendMany(List<SendMessageDto> sendMessageDtos) throws IOException {
        log.info("发送{}消息到消息队列中", sendMessageDtos.size());
        for (SendMessageDto sendMessageDto : sendMessageDtos) {
            send(sendMessageDto);
        }
    }

    /**
     * @Author hmr
     * @Description 发送一个
     * @Date: 2020/1/15 16:40
     * @param sendMessageDto
     * @reture: void
     **/
    private void send(SendMessageDto sendMessageDto) throws IOException {
        validate(sendMessageDto, annotation.isSaveToRedis());
        String body = new ObjectMapper().writeValueAsString(sendMessageDto);
        //接收该消息的队列
        String queueType = sendMessageDto.getType().toLowerCase().contains(RabbitMqConfig.QUEUE_INFORM_SMS)?RabbitMqConfig.QUEUE_INFORM_SMS:RabbitMqConfig.QUEUE_INFORM_EMAIL;
        log.info("向{}消息队列中发送消息,消息体为{}",queueType, body);
        //校验参数
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM, "info.1.email.1", body);
        if(annotation.isSaveToRedis()){
            log.info("向redis中存入值, key为{},value为{}", sendMessageDto.getRedisKey(), sendMessageDto.getValue());
            redisTemplate.opsForValue().set(sendMessageDto.getRedisKey(), sendMessageDto.getValue(), sendMessageDto.getExpiration(), sendMessageDto.getUnit());
        }
    }

    /**
     * @Author hmr
     * @Description 校验实体类中的数据是否合法
     * @Date: 2020/1/15 10:12
     * @param sendMessageDto 发送消息的实体类
     * @param flag 是否需要存储到redis中
     **/
    private void validate(SendMessageDto sendMessageDto, boolean flag){
        if(null == sendMessageDto){
            log.error("校验参数--->代理方法中没有SendMessageDto的形参");
            throw new GlobalException("服务器内部错误,参数异常");
        }

        //需要存储到redis中，需要校验redisKey、value、expiration、unit
        if(flag){
            if(StringUtils.isBlank(sendMessageDto.getRedisKey())){
                log.error("校验参数--->redis中的key不能为空");
                throw new GlobalException("服务器内部错误,参数异常");
            }
            if(sendMessageDto.getExpiration()==null){
                log.error("校验参数--->redis中的过期时间不能为空");
                throw new GlobalException("服务器内部错误,参数异常");
            }
            if(null == sendMessageDto.getUnit()){
                log.error("校验参数--->redis中的过期时间单位不能为空");
                throw new GlobalException("服务器内部错误,参数异常");
            }
            if(StringUtils.isEmpty(sendMessageDto.getValue())){
                log.error("校验参数--->redis中的value不能为空");
                throw new GlobalException("服务器内部错误,参数异常");
            }
        }

        String queueType = sendMessageDto.getType();
        if(StringUtils.isBlank(queueType)){
            log.error("校验参数--->mq中的queueType不能为空");
            throw new GlobalException("服务器内部错误,参数异常");
        }
    }

    private MqGenerate getAnnotation(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        targetMethod = methodSignature.getMethod();
        return targetMethod.getAnnotation(MqGenerate.class);
    }
}
