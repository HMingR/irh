package top.imuster.message.provider.mqListeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CenterQueueListener
 * @Description: 消息中心队列监听器
 * @author: hmr
 * @date: 2020/1/17 18:07
 */
@Component
public class CenterQueueListener {
    private static final String QUEUE_NAME = "queue_inform_center";

    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg){

    }

}
