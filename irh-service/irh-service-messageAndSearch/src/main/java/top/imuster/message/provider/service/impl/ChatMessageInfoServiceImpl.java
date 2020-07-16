package top.imuster.message.provider.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.message.pojo.ChatMessageInfo;
import top.imuster.message.provider.component.ChatWebSocket;
import top.imuster.message.provider.dao.ChatMessageInfoDao;
import top.imuster.message.provider.service.ChatMessageInfoService;

import javax.annotation.Resource;

/**
 * ChatMessageInfoService 实现类
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
@Service("chatMessageInfoService")
public class ChatMessageInfoServiceImpl extends BaseServiceImpl<ChatMessageInfo, Long> implements ChatMessageInfoService {

    @Resource
    private ChatMessageInfoDao chatMessageInfoDao;

    @Autowired
    ChatWebSocket chatWebSocket;

    @Override
    public BaseDao<ChatMessageInfo, Long> getDao() {
        return this.chatMessageInfoDao;
    }

    @Override
    public Message<String> push(ChatMessageInfo chatMessageInfo) {
        return null;
    }
}