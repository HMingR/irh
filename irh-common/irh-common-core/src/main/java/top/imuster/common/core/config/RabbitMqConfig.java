package top.imuster.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName: RabbitMqConfig
 * @Description: 消息队列配置类
 * @author: hmr
 * @date: 2020/1/12 10:47
 */
@Configuration
@Slf4j
public class RabbitMqConfig {
    //发送邮件的消息队列
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";

    //当会员修改商品信息时需要修改静态页面
    public static final String QUEUE_INFORM_SMS = "queue_inform_detail";

    //交换机名称
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    @Bean(QUEUE_INFORM_EMAIL)
    public Queue emailQueue(){
        return new Queue(QUEUE_INFORM_EMAIL, true);
    }

    @Bean(QUEUE_INFORM_SMS)
    public Queue smsQueue(){
        return new Queue(QUEUE_INFORM_SMS, true);
    }

    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    @Bean
    public Binding emailQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(emailQueue()).to(exchange).with("info.#.email.#").noargs();
    }

    @Bean
    public Binding smsQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(smsQueue()).to(exchange).with("info.#.detail.#").noargs();
    }
}
