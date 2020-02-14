package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.forum.api.pojo.ForumHotTopic;
import top.imuster.life.provider.dao.ForumHotTopicDao;

/**
 * ForumHotTopicDao 实现类
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
@Repository("forumHotTopicDao")
public class ForumHotTopicDaoImpl extends BaseDaoImpl<ForumHotTopic, Long> implements ForumHotTopicDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ForumHotTopicDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}