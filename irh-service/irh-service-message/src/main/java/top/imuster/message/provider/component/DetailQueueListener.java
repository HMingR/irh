package top.imuster.message.provider.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: DetailQueueListener
 * @Description: 生成详情页队列监听器
 * @author: hmr
 * @date: 2020/1/17 18:07
 */
@Component
public class DetailQueueListener {
    private static final String QUEUE_NAME = "queue_inform_detail";

    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg){

    }
}
