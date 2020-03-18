package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ForumHotTopicInfo;
import top.imuster.life.provider.dao.ForumHotTopicDao;

import java.util.List;

/**
 * ForumHotTopicDao 实现类
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
@Repository("forumHotTopicDao")
public class ForumHotTopicDaoImpl extends BaseDaoImpl<ForumHotTopicInfo, Long> implements ForumHotTopicDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ForumHotTopicDao.";
	private final static String SELECT_SCORE_BY_TARGET_ID = "selectScoreByTargetId";
	private final static String UPDATE_SCORE_BY_TARGET_ID = "updateScoreByTargetId";
	private final static String SELECT_MAX_SCORE_TOP = "selectMaxScoreTop";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long selectScoreByTargetId(Long i) {
		return this.select(getNameSpace(SELECT_SCORE_BY_TARGET_ID), i);
	}

	@Override
	public void updateScoreByTargetId(ForumHotTopicInfo condition) {
		this.update(getNameSpace(UPDATE_SCORE_BY_TARGET_ID), condition);
	}

	@Override
	public List<ForumHotTopicInfo> selectMaxScoreTop(int topic) {
		return this.selectList(getNameSpace(SELECT_MAX_SCORE_TOP), topic);
	}

}