package top.imuster.message.provider.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.SendEmailDto;

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
        /*MimeMessage mimeMailMessage = null;
        mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setFrom("huangmingren@irh.onexmail.com");
        mimeMessageHelper.setTo(sendEmailDto.getEmail());
        mimeMessageHelper.setSubject(sendEmailDto.getSubject() == null ? "irh通知" : sendEmailDto.getSubject());
*/
        /*Map<String, Object> model = new HashMap<>();
        model.put("context",sendEmailDto.getContent());
        model.put("date", sendEmailDto.getDate() == null ? DateUtil.now() : sendEmailDto.getDate());
        Template template = configuration.getTemplate(sendEmailDto.getTemplateEnum().getTemplateLocation());
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        mimeMessageHelper.setText(text, true);*/

        //javaMailSender.send(mimeMailMessage);

        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("huangmingren@irh.onexmail.com"); //发送者邮箱地址
        message.setTo(sendEmailDto.getEmail()); //收件人邮箱地址
        message.setSubject(sendEmailDto.getSubject() == null ? "irh通知" : sendEmailDto.getSubject());
        if(sendEmailDto.getTemplateEnum().equals(TemplateEnum.USER_REGISTER)){
            message.setText(new StringBuffer().append("尊敬的irh平台用户, 您好!  您正在进行注册操作。此操作的验证码是").append(sendEmailDto.getContent()).append("请将该验证码回填到验证页面，完成身份验证, 10分钟内有效。").toString());
        }else if(sendEmailDto.getTemplateEnum().equals(TemplateEnum.USER_LOGIN)){
            message.setText(new StringBuffer("尊敬的irh平台用户, 您好!   您正在进行登录操作。此操作的验证码是").append(sendEmailDto.getContent()).append("请将该验证码回填到验证页面，完成身份验证, 10分钟内有效。").toString());
        }else{
            message.setText(sendEmailDto.getContent());
        }
        javaMailSender.send(message);*/
    }
}
