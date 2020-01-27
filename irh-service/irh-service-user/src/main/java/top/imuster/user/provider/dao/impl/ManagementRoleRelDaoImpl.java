package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.dao.ManagementRoleRelDao;

/**
 * ManagementRoleRelDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("managementRoleRelDao")
public class ManagementRoleRelDaoImpl extends BaseDaoImpl<UserRoleRel, Long> implements ManagementRoleRelDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.ManagementRoleRelDao.";
	private final static String SELECT_MANAGEMENT_ROLEINFO_BY_MANAGEMENTID = "selectManagementRoleInfoByManagementId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public RoleInfo selectManagementRoleInfoByManagementId(Long managementId){
		return this.select(getNameSpace(SELECT_MANAGEMENT_ROLEINFO_BY_MANAGEMENTID), managementId);
	}
}