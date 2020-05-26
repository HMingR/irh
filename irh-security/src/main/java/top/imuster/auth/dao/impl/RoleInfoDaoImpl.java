package top.imuster.auth.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.auth.dao.RoleInfoDao;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.security.api.pojo.RoleInfo;

import java.util.List;

/**
 * RoleInfoDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("roleInfoDao")
public class RoleInfoDaoImpl extends BaseDaoImpl<RoleInfo, Long> implements RoleInfoDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.RoleInfoDao.";
	private final static String SELECT_ROLE_AUTH_BY_ROLEID = "selectRoleAndAuthByRoleId";
	private final static String SELECT_ROLE_AND_AUTH = "selectRoleAndAuth";
	private final static String SELECT_OTHER_ROLE_BY_ADMIN_ID = "selectOtherRoleByAdminId";
	private final static String SELECT_ROLE_AND_AUTH_BY_ROLE_NAME = "selectRoleAndAuthByRoleName";

	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public RoleInfo selectRoleAndAuthByRoleId(Long roleId) {
		return this.select(getNameSpace(SELECT_ROLE_AUTH_BY_ROLEID), roleId);
	}

	@Override
	public List<RoleInfo> selectRoleAndAuth() {
		return this.selectList(getNameSpace(SELECT_ROLE_AND_AUTH), null);
	}

	@Override
	public List<RoleInfo> selectOtherRoleByAdminId(Long adminId) {
		return this.selectList(getNameSpace(SELECT_OTHER_ROLE_BY_ADMIN_ID), adminId);
	}

	@Override
	public RoleInfo selectRoleAndAuthByRoleName(String roleName) {
		return this.select(getNameSpace(SELECT_ROLE_AND_AUTH_BY_ROLE_NAME), roleName);
	}
}