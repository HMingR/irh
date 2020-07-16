package top.imuster.message.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.message.pojo.ChatSessionInfo;

import java.util.HashMap;

/**
 * ChatSessionInfoDao 接口
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
public interface ChatSessionInfoDao extends BaseDao<ChatSessionInfo, Long> {

    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据接收方和发送方的id查看是否之前有聊天记录,有则返回sessionCode，没有则返回null
     * @Date: 2020/7/15 10:19
     * @param params
     * @reture: int
     **/
    String selectHasSessionHistory(HashMap<String, Long> params);

}