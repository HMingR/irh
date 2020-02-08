package top.imuster.forum.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.forum.api.pojo.ArticleCollection;
import top.imuster.forum.provider.dao.ArticleCollectionDao;

/**
 * ArticleCollectionDao 实现类
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@Repository("articleCollectionDao")
public class ArticleCollectionDaoImpl extends BaseDaoImpl<ArticleCollection, Long> implements ArticleCollectionDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.ArticleCollectionDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}