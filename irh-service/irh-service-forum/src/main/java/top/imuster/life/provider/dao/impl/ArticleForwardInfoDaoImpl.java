package top.imuster.life.provider.dao.impl;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.pojo.ArticleForwardInfo;
import top.imuster.life.provider.dao.ArticleForwardInfoDao;

import javax.annotation.Resource;
import java.util.List;

/**
 * ArticleForwardInfoDao 实现类
 * @author 黄明人
 * @since 2020-02-21 17:23:45
 */
@Repository("articleForwardInfoDao")
public class ArticleForwardInfoDaoImpl extends BaseDaoImpl<ArticleForwardInfo, Long> implements ArticleForwardInfoDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ArticleForwardInfoDao.";

	@Resource
	SqlSessionTemplate batchSqlSessionTemplate;

	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}