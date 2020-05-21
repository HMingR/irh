package top.imuster.message.provider.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import top.imuster.common.core.dto.rabbitMq.SendEmailDto;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

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
    private JavaMailSender javaMailSender;

    @Autowired
    Configuration configuration;


    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg) throws Exception{
        SendEmailDto sendEmailDto = objectMapper.readValue(msg, SendEmailDto.class);
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom("irhmail@imuster.top", "irh");
            mimeMessageHelper.setTo(sendEmailDto.getEmail());
            mimeMessageHelper.setSubject(sendEmailDto.getSubject() == null ? "irh平台通知" : sendEmailDto.getSubject());

            Map<String, Object> model = new HashMap<>();
            model.put("context", sendEmailDto.getContent());
            model.put("date", sendEmailDto.getDate());
            Template template = configuration.getTemplate(sendEmailDto.getTemplateEnum().getTemplateLocation());
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            System.out.println(text);
            mimeMessageHelper.setText(text, true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败{}", e.getMessage());
        }
    }
}
