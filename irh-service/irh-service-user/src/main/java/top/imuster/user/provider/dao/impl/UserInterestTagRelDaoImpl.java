package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.UserInterestTagRel;
import top.imuster.user.provider.dao.UserInterestTagRelDao;

/**
 * UserInterestTagRelDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("userInterestTagRelDao")
public class UserInterestTagRelDaoImpl extends BaseDaoImpl<UserInterestTagRel, Long> implements UserInterestTagRelDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.UserInterestTagRelDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}