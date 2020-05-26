package top.imuster.auth.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.auth.dao.UserLoginInfoDao;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.security.api.pojo.UserLoginInfo;

/**
 * UserLoginInfoDao 实现类
 * @author 黄明人
 * @since 2020-05-26 09:40:11
 */
@Repository("userLoginInfoDao")
public class UserLoginInfoDaoImpl extends BaseDaoImpl<UserLoginInfo, Long> implements UserLoginInfoDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.UserLoginInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}