package top.imuster.user.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.user.dao.AuthRoleRelDao;
import top.imuster.user.pojo.AuthRoleRel;

/**
 * AuthRoleRelDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("authRoleRelDao")
public class AuthRoleRelDaoImpl extends BaseDaoImpl<AuthRoleRel, Long> implements AuthRoleRelDao {
	private final static String NAMESPACE = "top.imuster.user.dao.AuthRoleRelDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}