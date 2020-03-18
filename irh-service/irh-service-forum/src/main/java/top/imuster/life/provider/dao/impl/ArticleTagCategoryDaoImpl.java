package top.imuster.life.provider.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ArticleTagCategoryInfo;
import top.imuster.life.provider.dao.ArticleTagCategoryDao;

/**
 * ArticleTagCategoryDao 实现类
 * @author 黄明人
 * @since 2020-02-13 17:26:48
 */
@Repository("articleTagCategoryDao")
public class ArticleTagCategoryDaoImpl extends BaseDaoImpl<ArticleTagCategoryInfo, Long> implements ArticleTagCategoryDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.ArticleTagCategoryDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}