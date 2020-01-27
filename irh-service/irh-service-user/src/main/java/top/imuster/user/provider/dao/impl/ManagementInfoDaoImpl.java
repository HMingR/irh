package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.provider.dao.ManagementInfoDao;

/**
 * ManagementInfoDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("managementInfoDao")
public class ManagementInfoDaoImpl extends BaseDaoImpl<ManagementInfo, Long> implements ManagementInfoDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.ManagementInfoDao.";
	private final static String SELECT_MANAGEMENT_ROLE_BY_CONDITION = "selectManagementRoleByCondition";


	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public ManagementInfo selectManagementRoleByCondition(ManagementInfo condition){
		return this.select(getNameSpace(SELECT_MANAGEMENT_ROLE_BY_CONDITION), condition);
	}
}