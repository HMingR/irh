package top.imuster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.common.core.config.RabbitMqConfig;
import top.imuster.user.provider.UserProviderApplication;

/**
 * @ClassName: RabbitMqUserUrlFilterInvocationSecurityMetadataSource
 * @Description: RabbitMqUserUrlFilterInvocationSecurityMetadataSource
 * @author: hmr
 * @date: 2020/5/24 9:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserProviderApplication.class)
public class RabbitMqUserUrlFilterInvocationSecurityMetadataSource {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test01(){
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,  "info.release.ARTICLE", "ARTICLE");
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,  "info.release.GOODS", "GOODS");
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM,  "info.release.DEMAND", "DEMAND");
    }

}
