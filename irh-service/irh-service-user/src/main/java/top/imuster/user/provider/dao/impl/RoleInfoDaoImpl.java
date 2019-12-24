package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.dao.RoleInfoDao;

import java.util.List;

/**
 * RoleInfoDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("roleInfoDao")
public class RoleInfoDaoImpl extends BaseDaoImpl<RoleInfo, Long> implements RoleInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.RoleInfoDao.";
	private final static String SELECT_ROLE_AUTH_BY_ROLEID = "selectRoleAndAuthByRoleId";
	private final static String SELECT_ROLE_AND_AUTH = "selectRoleAndAuth";

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
}