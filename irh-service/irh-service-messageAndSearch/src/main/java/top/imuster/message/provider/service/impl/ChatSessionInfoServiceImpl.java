package top.imuster.message.provider.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.utils.UuidUtils;
import top.imuster.message.pojo.ChatSessionInfo;
import top.imuster.message.provider.dao.ChatSessionInfoDao;
import top.imuster.message.provider.service.ChatSessionInfoService;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * ChatSessionInfoService 实现类
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
@Service("chatSessionInfoService")
public class ChatSessionInfoServiceImpl extends BaseServiceImpl<ChatSessionInfo, Long> implements ChatSessionInfoService {

    @Resource
    private ChatSessionInfoDao chatSessionInfoDao;

    @Override
    public BaseDao<ChatSessionInfo, Long> getDao() {
        return this.chatSessionInfoDao;
    }

    @Override
    public String checkSession(Long currentUserId, Long toUserId) {
        HashMap<String, Long> params = new HashMap<>();
        params.put("currentUserId", currentUserId);
        params.put("toUserId", toUserId);
        String sessionCode = chatSessionInfoDao.selectHasSessionHistory(params);
        if(StringUtils.isBlank(sessionCode)){
            sessionCode = String.valueOf(UuidUtils.nextId());
            ChatSessionInfo chatSessionInfo = new ChatSessionInfo();
            chatSessionInfo.setSessionCode(sessionCode);
            chatSessionInfo.setSponsorId(currentUserId);
            chatSessionInfo.setReceiverId(toUserId);
            chatSessionInfoDao.insertEntry(chatSessionInfo);
        }
        return sessionCode;
    }
}