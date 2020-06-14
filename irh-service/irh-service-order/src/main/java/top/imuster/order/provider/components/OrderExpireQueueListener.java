package top.imuster.order.provider.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.rabbitMq.SendOrderExpireDto;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: OrderExpireQueueListener
 * @Description: OrderExpireQueueListener
 * @author: hmr
 * @date: 2020/6/14 19:50
 */
@Component
public class OrderExpireQueueListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String QUEUE_NAME = "queue_dlx_order_expire";

    @Autowired
    private ObjectMapper objectMapper;

    @Resource
    OrderInfoService orderInfoService;


    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg){
        try{
            SendOrderExpireDto sendMessageDto = objectMapper.readValue(msg, SendOrderExpireDto.class);
            orderInfoService.cancelOrder(sendMessageDto.getOrderId(), sendMessageDto.getUserId(),  4);
        }catch (Exception e){
            log.error("关闭订单失败,错误信息为{}", e.getMessage(), e);
        }
    }
}
