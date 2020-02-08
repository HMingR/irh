package top.imuster.forum.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.provider.dao.ArticleInfoDao;

import java.util.List;

/**
 * ArticleInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Repository("articleInfoDao")
public class ArticleInfoDaoImpl extends BaseDaoImpl<ArticleInfo, Long> implements ArticleInfoDao {
	private final static String NAMESPACE = "top.imuster.forum.provider.dao.ArticleInfoDao.";
	private final static String SELECT_LIST_BY_CONDITION = "selectListByCondition";
	private final static String SELECT_UP_TOTAL_BY_IDS = "selectUpTotalByIds";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ArticleInfo> selectListByCondition(ArticleInfo articleInfo) {
		return this.selectList(getNameSpace(SELECT_LIST_BY_CONDITION), articleInfo);
	}

	@Override
	public List<ArticleInfo> selectUpTotalByIds(Long[] ids) {
		return this.selectList(getNameSpace(SELECT_UP_TOTAL_BY_IDS), ids);
	}
}