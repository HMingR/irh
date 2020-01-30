package top.imuster.forum.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.forum.api.pojo.ArticleReview;
import top.imuster.forum.provider.dao.ArticleReviewDao;

/**
 * ArticleReviewDao 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Repository("articleReviewDao")
public class ArticleReviewDaoImpl extends BaseDaoImpl<ArticleReview, Long> implements ArticleReviewDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.ArticleReviewDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}