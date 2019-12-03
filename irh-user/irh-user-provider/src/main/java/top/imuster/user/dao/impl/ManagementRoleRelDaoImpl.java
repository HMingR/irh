package top.imuster.user.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.user.dao.ManagementRoleRelDao;
import top.imuster.user.pojo.ManagementRoleRel;
import top.imuster.user.pojo.RoleInfo;

/**
 * ManagementRoleRelDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("managementRoleRelDao")
public class ManagementRoleRelDaoImpl extends BaseDaoImpl<ManagementRoleRel, Long> implements ManagementRoleRelDao {
	private final static String NAMESPACE = "top.imuster.user.dao.ManagementRoleRelDao.";
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