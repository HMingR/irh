package top.imuster.message.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.message.pojo.ChatMessageInfo;
import top.imuster.message.provider.dao.ChatMessageInfoDao;

/**
 * ChatMessageInfoDao 实现类
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
@Repository("chatMessageInfoDao")
public class ChatMessageInfoDaoImpl extends BaseDaoImpl<ChatMessageInfo, Long> implements ChatMessageInfoDao {
	private final static String NAMESPACE = "top.imuster.message.provider.dao.ChatMessageInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}