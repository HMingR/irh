package top.imuster.common.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.imuster.common.core.enums.MqTypeEnum;

/**
 * @ClassName: RabbitMqConfig
 * @Description: 消息队列配置类
 * @author: hmr
 * @date: 2020/1/12 10:47
 */
@Configuration
@Slf4j
public class RabbitMqConfig {
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

    @Bean
    public Queue emailQueue(){
        return new Queue(MqTypeEnum.EMAIL.getQueueName(), true);
    }

    @Bean
    public Queue smsQueue(){
        return new Queue(MqTypeEnum.DETAIL.getQueueName(), true);
    }

    @Bean
    public Queue centerQueue(){
        return new Queue(MqTypeEnum.CENTER.getQueueName(), true);
    }

    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    @Bean
    public Binding emailQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(emailQueue()).to(exchange).with(MqTypeEnum.EMAIL.getRoutingKeyMatchRule()).noargs();
    }

    @Bean
    public Binding smsQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(smsQueue()).to(exchange).with(MqTypeEnum.DETAIL.getRoutingKeyMatchRule()).noargs();
    }

    @Bean
    public Binding centerQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(centerQueue()).to(exchange).with(MqTypeEnum.CENTER.getRoutingKeyMatchRule()).noargs();
    }

}
