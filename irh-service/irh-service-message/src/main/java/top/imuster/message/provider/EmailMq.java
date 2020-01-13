package top.imuster.message.provider;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.imuster.common.core.config.RabbitMqConfig;

/**
 * @ClassName: EmailMq
 * @Description: 监听消息队列中含有发送邮箱的消息
 * @author: hmr
 * @date: 2019/12/30 16:28
 */
@Component
public class EmailMq {

    @RabbitListener(queues = {RabbitMqConfig.QUEUE_INFORM_EMAIL})
    public void sendEmail(String msg){
        System.out.println(msg + "----" + "---");
    }

}
