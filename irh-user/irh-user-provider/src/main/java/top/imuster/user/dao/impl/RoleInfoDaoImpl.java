package top.imuster.user.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.user.dao.RoleInfoDao;
import top.imuster.user.pojo.RoleInfo;

/**
 * RoleInfoDao 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Repository("roleInfoDao")
public class RoleInfoDaoImpl extends BaseDaoImpl<RoleInfo, Long> implements RoleInfoDao {
	private final static String NAMESPACE = "top.imuster.user.dao.RoleInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}