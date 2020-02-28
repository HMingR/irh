package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ArticleTagRel;
import top.imuster.life.provider.dao.ArticleTagRelDao;

import java.util.List;

/**
 * ArticleTagRelDao 实现类
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
@Repository("articleTagRelDao")
public class ArticleTagRelDaoImpl extends BaseDaoImpl<ArticleTagRel, Long> implements ArticleTagRelDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ArticleTagRelDao.";
	private final static String SELECT_TAG_NAMES_BY_ARTICLE_ID = "selectTagNameByArticleId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<String> selectTagNameByArticleId(Long id) {
		return this.selectList(getNameSpace(SELECT_TAG_NAMES_BY_ARTICLE_ID), id);
	}
}