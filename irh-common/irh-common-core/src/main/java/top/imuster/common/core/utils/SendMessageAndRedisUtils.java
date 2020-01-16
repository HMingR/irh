package top.imuster.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.dto.SendMessageDto;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @ClassName: SendMessageAndRedisUtils
 * @Description: 发送消息到rabbitMq和redis中的工具类
 * @author: hmr
 * @date: 2020/1/16 19:07
 */
@Component
@Slf4j
public class SendMessageAndRedisUtils {

    private static final SendMessageAndRedisUtils sendMessageAndRedisUtils = new SendMessageAndRedisUtils();

    private SendMessageAndRedisUtils(){}

    public static void generateSendMq(SendMessageDto sendMessageDto){
        sendMessageAndRedisUtils.sendMq(sendMessageDto);
    }

    public static void main(String[] args) {
        generateSendMq(new SendMessageDto());
    }



    @MqGenerate
    public void sendMq(SendMessageDto sendMessageDto){
        log.info("发送单个消息,消息实体为{}", sendMessageDto);
    }

    @MqGenerate(one = false)
    public void sendMq(List<SendMessageDto> sendMessageDtos){
        log.info("发送多个消息,消息总数为{}", sendMessageDtos.size());
    }

    @MqGenerate
    public void sendMqAndRedis(SendMessageDto sendMessageDto){
        log.info("发送单个消息到mq和redis中,消息实体为{}",sendMessageDto);
    }

    @MqGenerate(one = false)
    public void sendMqAndRedis(List<SendMessageDto> sendMessageDtos){
        log.info("发送多个消息到mq和redis中,消息总数为{}", sendMessageDtos.size());
    }
}
