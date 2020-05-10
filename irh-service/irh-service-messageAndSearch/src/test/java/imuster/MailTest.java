package imuster;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.spring5.SpringTemplateEngine;
import top.imuster.message.provider.MessageProviderApplication;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MailTest
 * @Description: MailTest
 * @author: hmr
 * @date: 2020/5/9 19:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageProviderApplication.class)
public class MailTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    Configuration configuration;

    //private Template template;

    @Test
    public void contextLoads() throws MessagingException {
        // 构造Email消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1978773465@qq.com");
        message.setTo("1978773465@qq.com");
        message.setSubject("邮件主题");
        message.setText("邮件内容");
        javaMailSender.send(message);
    }

    @Test
    public void sendTemplateMail() {

        MimeMessage mimeMailMessage = null;
        try {
            //template = configuration.getTemplate("Simple.ftl", "UTF-8");
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom("irh");
            mimeMessageHelper.setTo("1978773465@qq.com");
            mimeMessageHelper.setSubject("验证码");

            Map<String, Object> model = new HashMap<>();
            model.put("context","测试");
            model.put("date", "today");
            Template template = configuration.getTemplate("Simple.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            System.out.println(text);
            mimeMessageHelper.setText(text, true);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败{}", e.getMessage());
        }

    }

}
