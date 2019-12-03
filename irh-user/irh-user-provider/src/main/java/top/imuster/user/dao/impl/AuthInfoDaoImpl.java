package top.imuster.user.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.user.dao.AuthInfoDao;
import top.imuster.user.pojo.AuthInfo;

/**
 * AuthInfoDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("authInfoDao")
public class AuthInfoDaoImpl extends BaseDaoImpl<AuthInfo, Long> implements AuthInfoDao {
	private final static String NAMESPACE = "top.imuster.user.dao.AuthInfoDao.";
	private final static String SELECT_AUTHINFO_BY_ID = "selectAuthInfoById";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public AuthInfo selectAuthInfoById(Long authInfoId){
		return this.select(SELECT_AUTHINFO_BY_ID, authInfoId);
	}
}