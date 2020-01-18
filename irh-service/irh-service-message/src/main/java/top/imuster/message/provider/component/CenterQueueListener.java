package top.imuster.message.provider.component;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.message.provider.service.NewsInfoService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: CenterQueueListener
 * @Description: 消息中心队列监听器
 * @author: hmr
 * @date: 2020/1/17 18:07
 */
@Component
public class CenterQueueListener {
    private static final String QUEUE_NAME = "queue_inform_center";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    NewsInfoService newsInfoService;

    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg) throws IOException {
        SendMessageDto sendMessageDto = objectMapper.readValue(msg, SendMessageDto.class);
        newsInfoService.writeFromMq(sendMessageDto);
    }

}
