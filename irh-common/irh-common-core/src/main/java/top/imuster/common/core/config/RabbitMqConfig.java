package top.imuster.common.core.config;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.imuster.common.core.enums.MqTypeEnum;

import java.util.Map;

/**
 * @ClassName: RabbitMqConfig
 * @Description: 消息队列配置类
 * @author: hmr
 * @date: 2020/1/12 10:47
 */
@Configuration
public class RabbitMqConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);
    //交换机名称
    public static final String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    //私信队列交换机
    public static final String DLX_EXCHANGE_INFORM = "dlx_exchange_inform";

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

    /**
     * @Author hmr
     * @Description 当MQ的消费端出现异常的时候不重复发消息
     * @Date: 2020/5/31 9:00
     * @param
     * @reture: org.springframework.amqp.rabbit.listener.RabbitListenerErrorHandler
     **/
    @Bean
    public RabbitListenerErrorHandler rabbitListenerErrorHandler(){
        return new RabbitListenerErrorHandler() {
            @Override
            public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
                log.error("--------->MQ消费端出现异常,将{}消息丢了", message);
                throw exception;
            }
        };
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(MqTypeEnum.EMAIL.getQueueName(), true);
    }

    @Bean
    public Queue authenRecordQueue(){return new Queue(MqTypeEnum.AUTHEN_RECORD.getQueueName(), true); }

    @Bean
    public Queue smsQueue(){
        return new Queue(MqTypeEnum.DETAIL.getQueueName(), true);
    }

    @Bean
    public Queue centerQueue(){
        return new Queue(MqTypeEnum.CENTER.getQueueName(), true);
    }

    @Bean
    public Queue errandQueue(){
        return new Queue(MqTypeEnum.ERRAND.getQueueName(), true);
    }

    @Bean
    public Queue releaseQueue(){
        return new Queue(MqTypeEnum.RELEASE.getQueueName(), true);
    }

    @Bean
    public Queue examineQueue(){
        return new Queue(MqTypeEnum.EXAMINE_INFO.getQueueName(), true);
    }


    /**
     * @Author hmr
     * @Description 下单成功之后进入该队列
     * @Date: 2020/6/14 19:38
     * @param
     * @reture: org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue orderWaitPayQueue(){
        Map<String, Object> argsMap = Maps.newHashMap();
        argsMap.put("x-dead-letter-exchange", DLX_EXCHANGE_INFORM);
        argsMap.put("x-message-ttl", 10 * 60 * 1000);
        argsMap.put("x-dead-letter-routing-key", "dlx_order");
        return new Queue("wait_pay_queue",true,false,false, argsMap);
    }

    /**
     * @Author hmr
     * @Description 订单超时队列
     * @Date: 2020/6/14 19:41
     * @param
     * @reture: org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue orderExpireQueue(){
        return new Queue(MqTypeEnum.ORDER_DLX.getQueueName(), true);
    }

    /**
     * @Author hmr
     * @Description 处理超时未支付的订单  业务队列
     * @Date: 2020/6/14 19:36
     * @reture: org.springframework.amqp.core.Binding
     **/
    @Bean
    public Binding orderExpireBinding() {
        return BindingBuilder.bind(orderExpireQueue()).to(deadExchange()).with(MqTypeEnum.ORDER_DLX.getRoutingKeyMatchRule()).noargs();
    }


    @Bean
    public Binding orderWaitPayBinding() {
        return BindingBuilder.bind(orderWaitPayQueue()).to(exchange()).with(MqTypeEnum.ORDER_DLX.getRoutingKey()).noargs();
    }

    /**
     * @Author hmr
     * @Description 订单自动完成死信队列
     * @Date: 2020/6/16 9:44
     * @param
     * @reture: org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue waitOrderEvaluateQueue(){
        Map<String, Object> argsMap = Maps.newHashMap();
        argsMap.put("x-dead-letter-exchange", DLX_EXCHANGE_INFORM);
        argsMap.put("x-message-ttl", 5 * 24 * 60 * 60 * 1000);
        argsMap.put("x-dead-letter-routing-key", "dlx_order_evaluate");
        return new Queue("wait_evaluate_queue",true,false,false, argsMap);
    }

    /**
     * @Author hmr
     * @Description 具体的业务队列
     * @Date: 2020/6/16 19:35
     * @param
     * @reture: org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue orderEvaluateQueue(){
        return new Queue(MqTypeEnum.ORDER_EVALUATE.getQueueName(), true);
    }

    /**
     * @Author hmr
     * @Description 订单自动完成死信息队列
     * @Date: 2020/6/14 19:36
     * @reture: org.springframework.amqp.core.Binding
     **/
    @Bean
    public Binding waitOrderEvaluateBinding() {
        return BindingBuilder.bind(waitOrderEvaluateQueue()).to(exchange()).with(MqTypeEnum.ORDER_EVALUATE.getRoutingKeyMatchRule()).noargs();
    }

    @Bean
    public Binding orderEvaluateBinding(){
        return BindingBuilder.bind(orderEvaluateQueue()).to(deadExchange()).with(MqTypeEnum.ORDER_EVALUATE.getRoutingKeyMatchRule()).noargs();
    }

    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    /**
     * @Author hmr
     * @Description 死信交换机
     * @Date: 2020/6/14 19:36
     * @param
     * @reture: org.springframework.amqp.core.Exchange
     **/
    @Bean(DLX_EXCHANGE_INFORM)
    public Exchange deadExchange(){
        return ExchangeBuilder.topicExchange(DLX_EXCHANGE_INFORM).durable(true).build();
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

    @Bean
    public Binding errandQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(errandQueue()).to(exchange).with(MqTypeEnum.ERRAND.getRoutingKeyMatchRule()).noargs();
    }

    @Bean
    public Binding authenRecordQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(authenRecordQueue()).to(exchange).with(MqTypeEnum.AUTHEN_RECORD.getRoutingKeyMatchRule()).noargs();
    }

    @Bean
    public Binding releaseQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(releaseQueue()).to(exchange).with(MqTypeEnum.RELEASE.getRoutingKeyMatchRule()).noargs();
    }

    @Bean
    public Binding examineQueueBinding(@Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(examineQueue()).to(exchange).with(MqTypeEnum.EXAMINE_INFO.getRoutingKeyMatchRule()).noargs();
    }

}
