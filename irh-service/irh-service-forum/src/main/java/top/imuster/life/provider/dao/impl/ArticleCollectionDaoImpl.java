package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ArticleCollection;
import top.imuster.life.provider.dao.ArticleCollectionDao;

import java.util.List;

/**
 * ArticleCollectionDao 实现类
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@Repository("articleCollectionDao")
public class ArticleCollectionDaoImpl extends BaseDaoImpl<ArticleCollection, Long> implements ArticleCollectionDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.ArticleCollectionDao.";
	private final static String SELECT_COLLECT_BY_CONDITION = "selectCollectByCondition";
	private final static String SELECT_TOTAL_BY_USER_ID = "selectTotalByUserId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ArticleCollection> selectCollectByCondition(ArticleCollection searchCondition) {
		return this.selectList(getNameSpace(SELECT_COLLECT_BY_CONDITION), searchCondition);
	}

	@Override
	public Long selectTotalByUserId(Long userId) {
		return this.select(getNameSpace(SELECT_TOTAL_BY_USER_ID), userId);
	}
}