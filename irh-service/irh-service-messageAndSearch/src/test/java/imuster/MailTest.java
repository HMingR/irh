package imuster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.spring5.SpringTemplateEngine;
import top.imuster.message.provider.MessageProviderApplication;

import javax.mail.MessagingException;

/**
 * @ClassName: MailTest
 * @Description: MailTest
 * @author: hmr
 * @date: 2020/5/9 19:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageProviderApplication.class)
public class MailTest {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

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

}
