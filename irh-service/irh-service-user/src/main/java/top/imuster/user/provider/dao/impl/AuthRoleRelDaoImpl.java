package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.AuthRoleRel;
import top.imuster.user.provider.dao.AuthRoleRelDao;

import java.util.List;

/**
 * AuthRoleRelDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("authRoleRelDao")
public class AuthRoleRelDaoImpl extends BaseDaoImpl<AuthRoleRel, Long> implements AuthRoleRelDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.AuthRoleRelDao.";
	private final static String SELECT_AUTH_LIST_BY_ROLEID= "selectAuthListByRoleId";
	private final static String DELETE_BY_CONDITION = "deleteByCondition";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<AuthInfo> selectAuthListByRoleId(Long id) {
		return this.select(getNameSpace(SELECT_AUTH_LIST_BY_ROLEID), id);
	}

	@Override
	public void deleteByCondition(AuthRoleRel condition) {
		this.delete(getNameSpace(DELETE_BY_CONDITION), condition);
	}
}