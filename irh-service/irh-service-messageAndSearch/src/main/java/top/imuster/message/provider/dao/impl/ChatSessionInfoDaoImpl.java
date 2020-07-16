package top.imuster.message.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.message.pojo.ChatSessionInfo;
import top.imuster.message.provider.dao.ChatSessionInfoDao;

import java.util.HashMap;

/**
 * ChatSessionInfoDao 实现类
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
@Repository("chatSessionInfoDao")
public class ChatSessionInfoDaoImpl extends BaseDaoImpl<ChatSessionInfo, Long> implements ChatSessionInfoDao {
	private final static String NAMESPACE = "top.imuster.message.provider.dao.ChatSessionInfoDao.";
	private final static String SELECT_HAS_SESSION_HISTORY = "selectHasSessionHistory";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public String selectHasSessionHistory(HashMap<String, Long> params) {
		return this.select(getNameSpace(SELECT_HAS_SESSION_HISTORY), params);
	}
}