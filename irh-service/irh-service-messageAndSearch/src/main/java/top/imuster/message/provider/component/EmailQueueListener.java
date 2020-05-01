package top.imuster.message.provider.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: EmailQueueListener
 * @Description: email队列监听器
 * @author: hmr
 * @date: 2020/1/17 18:06
 */
@Component
public class EmailQueueListener {
    private static final Logger log = LoggerFactory.getLogger(EmailQueueListener.class);

    private static final String QUEUE_NAME = "queue_inform_email";

    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg){
        log.info("信息为{}", msg);
    }
}
