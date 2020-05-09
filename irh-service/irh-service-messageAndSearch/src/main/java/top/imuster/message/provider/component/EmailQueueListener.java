package top.imuster.message.provider.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import top.imuster.common.core.dto.SendEmailDto;

import java.io.IOException;

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

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TemplateEngine templateEngine;

    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg) throws IOException {
        SendEmailDto sendEmailDto = objectMapper.readValue(msg, SendEmailDto.class);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送人
        simpleMailMessage.setFrom("irh平台");
        //邮件接收人
        simpleMailMessage.setTo(sendEmailDto.getEmail());
        //邮件主题
        simpleMailMessage.setSubject("通知");
        //邮件内容
        simpleMailMessage.setText(sendEmailDto.getContent());
    }
}
