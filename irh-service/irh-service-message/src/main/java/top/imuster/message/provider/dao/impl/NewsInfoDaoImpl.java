package top.imuster.message.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.dao.NewsInfoDao;

/**
 * NewsInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
@Repository("newsInfoDao")
public class NewsInfoDaoImpl extends BaseDaoImpl<NewsInfo, Long> implements NewsInfoDao {
	private final static String NAMESPACE = "top.imuster.message.provider.dao.NewsInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}