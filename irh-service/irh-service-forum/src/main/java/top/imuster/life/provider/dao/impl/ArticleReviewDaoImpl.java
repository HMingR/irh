package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ArticleReviewInfo;
import top.imuster.life.provider.dao.ArticleReviewDao;

import java.util.List;

/**
 * ArticleReviewDao 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Repository("articleReviewDao")
public class ArticleReviewDaoImpl extends BaseDaoImpl<ArticleReviewInfo, Long> implements ArticleReviewDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ArticleReviewDao.";
	private final static String SELECT_USER_ID_BY_REVIEW_ID = "selectUserIdByReviewId";
	private final static String SELECT_UP_TOTAL_BY_IDS = "selectUpTotalByIds";
	private final static String SELECT_UP_TOTAL_BY_ID = "selectUpTotalById";
	private final static String SELECT_FIRST_CLASS_REVIEW_BY_ARTICLE = "selectFirstClassReviewByArticleId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long selectUserIdByReviewId(Long targetId) {
		return this.select(getNameSpace(SELECT_USER_ID_BY_REVIEW_ID), targetId);
	}

	@Override
	public List<ArticleReviewInfo> selectUpTotalByIds(Long[] reviewIds) {
		return selectList(getNameSpace(SELECT_UP_TOTAL_BY_IDS), reviewIds);
	}

	@Override
	public Long selectUpTotalById(Long id) {
		return this.select(getNameSpace(SELECT_UP_TOTAL_BY_ID), id);
	}

	@Override
	public List<ArticleReviewInfo> selectFirstClassReviewByArticleId(ArticleReviewInfo searchCondition) {
		return this.selectList(getNameSpace(SELECT_FIRST_CLASS_REVIEW_BY_ARTICLE), searchCondition);
	}
}