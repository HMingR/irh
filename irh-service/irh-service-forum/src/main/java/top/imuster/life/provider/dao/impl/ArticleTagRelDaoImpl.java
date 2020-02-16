package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.forum.api.pojo.ArticleTagRel;
import top.imuster.life.provider.dao.ArticleTagRelDao;

/**
 * ArticleTagRelDao 实现类
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
@Repository("articleTagRelDao")
public class ArticleTagRelDaoImpl extends BaseDaoImpl<ArticleTagRel, Long> implements ArticleTagRelDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ArticleTagRelDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}