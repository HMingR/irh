package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.provider.dao.ArticleCategoryDao;

/**
 * ArticleCategoryDao 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Repository("articleCategoryDao")
public class ArticleCategoryDaoImpl extends BaseDaoImpl<ArticleCategoryInfo, Long> implements ArticleCategoryDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ArticleCategoryDao.";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}