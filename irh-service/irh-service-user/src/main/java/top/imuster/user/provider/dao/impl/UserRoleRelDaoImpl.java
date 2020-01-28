package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.dao.UserRoleRelDao;

/**
 * UserRoleRelDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("userRoleRelDao")
public class UserRoleRelDaoImpl extends BaseDaoImpl<UserRoleRel, Long> implements UserRoleRelDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.UserRoleRelDao.";
	private final static String SELECT_USER_ROLE_INFO_BY_USER_ID = "selectUserRoleInfoByUserId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public RoleInfo selectUserRoleInfoByUserId(Long userId){
		return this.select(getNameSpace(SELECT_USER_ROLE_INFO_BY_USER_ID), userId);
	}
}