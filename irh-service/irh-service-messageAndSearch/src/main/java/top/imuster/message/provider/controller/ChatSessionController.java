package top.imuster.message.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.message.provider.service.ChatSessionInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ChatSessionController
 * @Description: ChatSessionController
 * @author: hmr
 * @date: 2020/7/15 11:17
 */

@RestController
@RequestMapping("/chat/session")
public class ChatSessionController extends BaseController {

    @Resource
    ChatSessionInfoService chatSessionInfoService;

    /**
     * @Author hmr
     * @Description 校验之前是否有聊天记录，如果有之前的会话，则返回会话session，没有则创建一个新的会话session
     * @Date: 2020/7/15 10:42
     * @param toUserId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.Integer>
     **/
    @GetMapping("/{toUserId}")
    public Message<String> checkSession(@PathVariable("toUserId") Long toUserId){
        Long currentUserId = getCurrentUserIdFromCookie();
        String sessionCode = chatSessionInfoService.checkSession(currentUserId, toUserId);
        return Message.createBySuccess(sessionCode);
    }
}
