package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.provider.dao.ArticleTagDao;

import java.util.List;

/**
 * ArticleCategoryDao 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Repository("articleTagDao")
public class ArticleTagDaoImpl extends BaseDaoImpl<ArticleCategoryInfo, Long> implements ArticleTagDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ArticleTagDao.";
	private final static String SELECT_TAG_ID_BY_CONDITION = "selectTagIdByCategory";
	private final static String SELECT_TAG_INFO_BY_CATEGORY_IDS = "selectTagInfoByCategoryIds";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<Long> selectTagIdByCategory(Long categoryId) {
		return this.selectList(getNameSpace(SELECT_TAG_ID_BY_CONDITION), categoryId);
	}

	@Override
	public List<ArticleCategoryInfo> selectTagInfoByCategoryIds(List<String> list) {
		return this.selectList(getNameSpace(SELECT_TAG_INFO_BY_CATEGORY_IDS), list);
	}
}