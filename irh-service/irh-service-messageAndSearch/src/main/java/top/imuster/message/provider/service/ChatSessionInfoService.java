package top.imuster.message.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.message.pojo.ChatSessionInfo;

/**
 * ChatSessionInfoService接口
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
public interface ChatSessionInfoService extends BaseService<ChatSessionInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据两个id查看之前是否有聊天记录
     * @Date: 2020/7/15 10:11
     * @reture: boolean
     **/
    String checkSession(Long currentUserId, Long toUserId);

}