package top.imuster.message.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MessageCenterController
 * @Description: 消息中心控制器
 * @author: hmr
 * @date: 2020/1/17 18:03
 */
@RestController
@RequestMapping("/msg")
public class MessageCenterController {

    @Autowired
    JavaMailSenderImpl mailSender;

    @GetMapping("/test")
    public void test(){
        String emailServiceCode = "1234";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("注册验证码");
        message.setFrom("1978773465@qq.com");
        message.setText("注册验证码是：" + emailServiceCode);
        message.setTo("2452035127@qq.com");
        mailSender.send(message);
    }

}
