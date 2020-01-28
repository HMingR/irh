package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.provider.dao.AuthInfoDao;

/**
 * AuthInfoDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("authInfoDao")
public class AuthInfoDaoImpl extends BaseDaoImpl<AuthInfo, Long> implements AuthInfoDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.AuthInfoDao.";
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