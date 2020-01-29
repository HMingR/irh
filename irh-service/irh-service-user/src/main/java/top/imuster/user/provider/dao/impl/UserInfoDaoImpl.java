package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.dao.UserInfoDao;

/**
 * UserInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo, Long> implements UserInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.UserInfoDao.";
	private final static String CHECK_INFO = "checkInfo";
	private final static String SELECT_EMAIL_BY_ID = "selectEmailById";
	private final static String SELECT_USER_ROLE_BY_CONDITION = "selectUserRoleByCondition";

	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public int checkInfo(UserInfo userInfo) {
		return this.select(getNameSpace(CHECK_INFO), userInfo);
	}

	@Override
	public String selectEmailById(Long id) {
		return this.select(getNameSpace(SELECT_EMAIL_BY_ID), id);
	}

	@Override
	public UserInfo selectUserRoleByCondition(UserInfo condition) {
		return this.select(getNameSpace(SELECT_USER_ROLE_BY_CONDITION), condition);
	}
}