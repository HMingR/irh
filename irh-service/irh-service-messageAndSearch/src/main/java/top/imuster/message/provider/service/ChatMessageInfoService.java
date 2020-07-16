package top.imuster.message.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.message.pojo.ChatMessageInfo;

/**
 * ChatMessageInfoService接口
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
public interface ChatMessageInfoService extends BaseService<ChatMessageInfo, Long> {

    /**
     * @Author hmr
     * @Description 发送消息
     * @Date: 2020/7/15 10:54
     * @param chatMessageInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> push(ChatMessageInfo chatMessageInfo);
}