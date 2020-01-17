package top.imuster.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.dto.SendMessageDto;

import java.util.ArrayList;

/**
 * @ClassName: GenerateSendMessageService
 * @Description: 发送消息
 * @author: hmr
 * @date: 2020/1/17 10:26
 */
@Service("generateSendMessageService")
@Slf4j
public class GenerateSendMessageService {

    /**
     * @Author hmr
     * @Description 发送单个消息到mq
     * @Date: 2020/1/17 10:33
     * @param sendMessageDto
     * @reture: void
     **/
    @MqGenerate
    public void sendToMq(SendMessageDto sendMessageDto){
        log.info("发送单个消息，消息内容为{}", sendMessageDto);
    }

    /**
     * @Author hmr
     * @Description 发送单个消息到mq和redis中
     * @Date: 2020/1/17 10:34
     * @param sendMessageDto
     * @reture: void
     **/
    @MqGenerate(isSaveToRedis = true)
    public void sendToMqAndReids(SendMessageDto sendMessageDto){
        log.info("发送消息到mq和redis中,实体内容为{}", sendMessageDto);
    }

    /**
     * @Author hmr
     * @Description 发送多个消息到mq中
     * @Date: 2020/1/17 10:34
     * @param sendMessageDtos
     * @reture: void
     **/
    @MqGenerate(one = false)
    public void senManyToMq(ArrayList<SendMessageDto> sendMessageDtos){
        log.info("发送多个消息到mq中,消息总数为{}", sendMessageDtos.size());
    }

    /**
     * @Author hmr
     * @Description 发送多个到mq和redis中
     * @Date: 2020/1/17 10:37
     * @param sendMessageDtos
     * @reture: void
     **/
    @MqGenerate(one = false, isSaveToRedis = true)
    public void sendManyToMqAndRedis(ArrayList<SendMessageDto> sendMessageDtos){
        log.info("发送多个消息到mq和redis中,消息的总数为{}",sendMessageDtos.size());
    }

}
