package top.imuster.message.provider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.service.NewsInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

/**
 * @ClassName: MessageCenterController
 * @Description: 消息中心控制器
 * @author: hmr
 * @date: 2020/1/17 18:03
 */
@RestController
@RequestMapping("/msg")
@Api("消息中心控制器")
public class MessageCenterController extends BaseController {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Resource
    NewsInfoService newsInfoService;

    @ApiOperation("分页查询消息，未读的排在前面")
    @GetMapping
    public Message list(HttpServletRequest request, Page<NewsInfo> page) throws Exception{
        NewsInfo newsInfo = new NewsInfo();
        Long userId = getIdByToken(request);
        newsInfo.setReceiverId(userId);
        newsInfo.setOrderField("state");
        newsInfo.setOrderFieldType("DESC");
        Page<NewsInfo> newsInfoPage = newsInfoService.selectPage(newsInfo, page);
        return Message.createBySuccess(newsInfoPage);
    }


    @ApiOperation("更新消息状态,type为10-删除 20-已读")
    @GetMapping("/type/id")
    public Message updateById(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setId(id);
        newsInfo.setState(type);
        newsInfoService.updateByKey(newsInfo);
        return Message.createBySuccess();
    }


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

    @GetMapping("/1")
    public void test2() throws ParseException {
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setType(MqTypeEnum.CENTER);
        sendMessageDto.setTopic("测试");
        sendMessageDto.setBody("测试内容");
        sendMessageDto.setSourceType(2);
        sendMessageDto.setSourceId(0L);
        sendMessageDto.setTargetId(234L);
        sendMessageDto.setSendDate(new Date());
        generateSendMessageService.sendToMq(sendMessageDto);

    }

}
