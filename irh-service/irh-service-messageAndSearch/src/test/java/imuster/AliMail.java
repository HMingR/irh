package imuster;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sun.mail.util.MailSSLSocketFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.message.provider.MessageProviderApplication;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @ClassName: AliMail
 * @Description: TODO
 * @author: hmr
 * @date: 2020/5/18 10:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageProviderApplication.class)
public class AliMail {

    @Autowired
    Configuration configuration;

    @Test
    public void test() {

        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的”cn-hangzhou”替换为”ap-southeast-1”、或”ap-southeast-2”。
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G3zewBvK2R2Qyw5AxxY", "OBHsox6CcmCtrtMJLjutDOdrFNXPFV")
        ;
        // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理
        //try {
        //DefaultProfile.addEndpoint(“dm.ap-southeast-1.aliyuncs.com”, “ap-southeast-1”, “Dm”,  “dm.ap-southeast-1.aliyuncs.com”);
        //} catch (ClientException e) {
        //e.printStackTrace();
        //}
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion(“2017-06-22”);// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName("irhmail@imuster.top");
            request.setFromAlias("irh登录验证码");
            request.setAddressType(1);
            request.setTagName("irh");
            request.setReplyToAddress(true);
            request.setToAddress("1978773465@qq.com");
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress(“邮箱1,邮箱2”);
            request.setSubject("验证码登录");
            //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
            //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误

            Map<String, Object> model = new HashMap<>();
            model.put("context","冠状病毒在系统分类上属套式病毒目（Nidovirales）冠状病毒科（Coronaviridae）冠状病毒属（Coronavirus）。冠状病毒属的病毒是具囊膜（envelope）、基因组为线性单股正链的RNA病毒，是自然界广泛存在的一大类病毒。\n" +
                    "冠状病毒直径约80～120nm，基因组5′端具有甲基化的帽状结构，3′端具有poly(A)尾，基因组全长约27-32kb，是目前已知RNA病毒中基因组最大的病毒。\n" +
                    "冠状病毒仅感染脊椎动物，如人、鼠、猪、猫、犬、狼、鸡、牛、禽类。 [1] \n" +
                    "冠状病毒最先是1937年从鸡身上分离出来，病毒颗粒的直径60～200nm，平均直径为100nm，呈球形或椭圆形，具有多形性。病毒有包膜，包膜上存在棘突，整个病毒像日冕，不同的冠状病毒的棘突有明显的差异。在冠状病毒感染细胞内有时可以见到管状的包涵体。 [2]");
            model.put("date", DateUtil.now());
            Template template = configuration.getTemplate("Simple.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            request.setHtmlBody(text);
            //SDK 采用的是http协议的发信方式, 默认是GET方法，有一定的长度限制。
            //若textBody、htmlBody或content的大小不确定，建议采用POST方式提交，避免出现uri is not valid异常
            request.setMethod(MethodType.POST);
            //开启需要备案，0关闭，1开启
            //request.setClickTrace(“0”);
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ServerException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void test002() throws IOException, TemplateException, MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();

        Map<String, Object> model = new HashMap<>();
        model.put("context","345g");
        model.put("date", DateUtil.now());
        Template template = configuration.getTemplate("UserLogin.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        message.setFrom(account); //发送者邮箱地址
        message.setTo("1978773465@qq.com"); //收件人邮箱地址
        message.setSubject("irh智慧平台修改密码");
        message.setText("验证码为:234");
        javaMailSender.send(message);

        /*MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("1978773465@qq.com");
        helper.setFrom(account);
        helper.setSubject("irh智慧平台登录验证");
        helper.setText(text, true);
        javaMailSender.send(message);*/
    }



    private static String account = "huangmingren@irh.onexmail.com";// 登录账户
    private static String password = "197877346Hmr";// 登录密码
    private static String host = "smtp.exmail.qq.com";// 服务器地址
    private static String port = "465";// 端口
    private static String protocol = "smtp";// 协议
    //初始化参数
    public static Session initProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        // 使用smtp身份验证
        properties.put("mail.smtp.auth", "true");
        // 使用SSL,企业邮箱必需 start
        // 开启安全协议
        MailSSLSocketFactory mailSSLSocketFactory = null;
        try {
            mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        properties.put("mail.smtp.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.socketFactory.port", port);
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        });
        // 使用SSL,企业邮箱必需 end
        // TODO 显示debug信息 正式环境注释掉
        session.setDebug(true);
        return session;
    }

    // @param sender 发件人别名
// @param subject 邮件主题
//@param content 邮件内容
//@param receiverList 接收者列表,多个接收者之间用","隔开
//@param fileSrc 附件地址
    @Test
    public void send() {
        try {
            Session session = initProperties();
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(account, "irh"));// 发件人,可以设置发件人的别名
            // 收件人,多人接收
            InternetAddress[] internetAddressTo = new InternetAddress().parse("1978773465@qq.com");
            mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
            // 主题
            mimeMessage.setSubject("验证码登录");
            // 时间
            mimeMessage.setSentDate(new Date());
            // 容器类 附件
            MimeMultipart mimeMultipart = new MimeMultipart();
            // 可以包装文本,图片,附件
            MimeBodyPart bodyPart = new MimeBodyPart();
            // 设置内容

            Map<String, Object> model = new HashMap<>();
            model.put("context","234h");
            model.put("date", DateUtil.now());
            Template template = configuration.getTemplate("UserLogin.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            bodyPart.setContent(text, "text/html; charset=UTF-8");
            mimeMultipart.addBodyPart(bodyPart);
            // 添加图片&附件
            bodyPart = new MimeBodyPart();
            //bodyPart.attachFile(fileSrc);
           // mimeMultipart.addBodyPart(bodyPart);
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test06(){
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        //端口
        prop.setProperty("mail.smtp.port", "465");
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //
        //获取Session对象
        Session s = Session.getDefaultInstance(prop,new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication("huangmingren@irh.onexmail.com", "197877346Hmr");
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
        s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            mimeMessage.setFrom(new InternetAddress("huangmingren@irh.onexmail.com","irh智慧校园平台"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("1978773465@qq.com"));
            //设置主题
            mimeMessage.setSubject("账户密码重置");
            mimeMessage.setSentDate(new Date());
            //设置内容
            Map<String, Object> model = new HashMap<>();
            model.put("context","irh验证登录");
            model.put("date", DateUtil.now());
            Template template = configuration.getTemplate("Simple.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessage.setContent("本次验证为123d，请在十分钟之内提交", "text/html; charset=UTF-8");
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
