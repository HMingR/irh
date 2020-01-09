package top.imuster.goods.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RabbitMqConfig
 * @Description: 商品模块的rabbitmq配置类
 * @author: hmr
 * @date: 2020/1/1 10:34
 */
@Component
public class RabbitMqConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${goods.queue.name}")
    private String queueName;

    @Value("${goods.exchange.name}")
    private String exchange;

    @Value("${goods.routing.key}")
    private String routingKey;

    @Bean("finishOrderQueue")
    public Queue finishOrderQueue(){
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange finishOrderExchange(){
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Binding finishOrderBinding(){
        return BindingBuilder.bind(finishOrderQueue()).to(finishOrderExchange()).with(routingKey);
    }

    /*@Bean("sendEmailRabbitMq")
    @Primary
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
        return rabbitTemplate;
    }*/




}
