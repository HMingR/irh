package top.imuster.message.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.message.pojo.ChatMessageInfo;
import top.imuster.message.provider.component.ChatWebSocket;
import top.imuster.message.provider.service.ChatMessageInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ChatController
 * @Description: 私聊控制器
 * @author: hmr
 * @date: 2020/7/15 9:28
 */
@RestController
@RequestMapping("/chat/msg")
public class ChatMessageController extends BaseController {

    @Autowired
    ChatWebSocket chatWebSocket;

    @Resource
    ChatMessageInfoService chatMessageInfoService;


    @PostMapping
    public Message<String> pushMsg(@RequestBody ChatMessageInfo chatMessageInfo){
        return chatMessageInfoService.push(chatMessageInfo);
    }

}
