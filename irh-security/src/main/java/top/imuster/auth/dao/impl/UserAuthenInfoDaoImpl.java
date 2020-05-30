package top.imuster.auth.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.auth.dao.UserAuthenInfoDao;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.security.api.pojo.UserAuthenInfo;

/**
 * UserAuthenInfoDao 实现类
 * @author 黄明人
 * @since 2020-05-30 17:35:12
 */
@Repository("userAuthenInfoDao")
public class UserAuthenInfoDaoImpl extends BaseDaoImpl<UserAuthenInfo, Long> implements UserAuthenInfoDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.UserAuthenInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}