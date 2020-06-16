package imuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.common.core.dto.rabbitMq.SendOrderEvaluateDto;
import top.imuster.common.core.dto.rabbitMq.SendOrderExpireDto;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.order.provider.IrhOrderApplication;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @ClassName: test
 * @Description: test
 * @author: hmr
 * @date: 2020/3/2 19:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrhOrderApplication.class)
public class test {

    @Autowired
    ObjectMapper objectMapper;

    @Resource
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Test
    public void test(){
        Map<String, String> user = userServiceFeignApi.getUserAddressAndPhoneById(5L);
        System.out.println(user.get("address"));
    }

    @Test
    public void test02() throws JsonProcessingException {
        SendOrderExpireDto sendOrderExpireDto = new SendOrderExpireDto();
        sendOrderExpireDto.setOrderId(1L);
        sendOrderExpireDto.setUserId(5L);
        sendOrderExpireDto.setTtl(5000L);
        generateSendMessageService.sendDeadMsg(sendOrderExpireDto);

        SendOrderEvaluateDto sendOrderEvaluateDto = new SendOrderEvaluateDto();
        sendOrderEvaluateDto.setOrderId(1L);
        sendOrderEvaluateDto.setTtl(5000L);
        sendOrderEvaluateDto.setUserId(5L);
        generateSendMessageService.sendDeadMsg(sendOrderEvaluateDto);
        /*rabbitTemplate.convertAndSend("dlx_exchange_inform", "dlx_order", objectMapper.writeValueAsString(sendOrderExpireDto), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().getHeaders().put("expiration", "5000");
                return message;
            }
        });*/
    }

}
