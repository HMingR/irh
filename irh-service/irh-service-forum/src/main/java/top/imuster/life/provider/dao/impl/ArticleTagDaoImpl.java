package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.forum.api.pojo.ArticleTag;
import top.imuster.life.provider.dao.ArticleTagDao;

/**
 * ArticleCategoryDao 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Repository("articleTagDao")
public class ArticleTagDaoImpl extends BaseDaoImpl<ArticleTag, Long> implements ArticleTagDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.ArticleCategoryDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}