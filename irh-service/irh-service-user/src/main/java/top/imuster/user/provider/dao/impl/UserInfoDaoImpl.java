package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.common.core.dto.UserDto;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.dao.UserInfoDao;

import java.util.Map;

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
	private final static String SELECT_USERNAME_BY_ID = "selectUserNameById";
	private final static String SELECT_USER_TOTAL_BY_CREATE_TIME = "selectUserTotalByCreateTime";
	private final static String SELECT_INCREMENT_USER_BY_TIME = "selectIncrementUserByTime";
	private final static String SELECT_USER_STATE_BY_ID = "selectUserStateById";
	private final static String SELECT_USER_DTO_BY_ID = "selectUserDtoById";
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

	@Override
	public String selectUserNameById(Long id) {
		return this.select(getNameSpace(SELECT_USERNAME_BY_ID), id);
	}

	@Override
	public long selectUserTotalByCreateTime(String s) {
		return this.select(getNameSpace(SELECT_USER_TOTAL_BY_CREATE_TIME), s);
	}

	@Override
	public Long selectIncrementUserByTime(Map<String, String> param) {
		return this.select(getNameSpace(SELECT_INCREMENT_USER_BY_TIME), param);
	}

	@Override
	public UserDto selectUserDtoById(Long userId) {
		return this.select(getNameSpace(SELECT_USER_DTO_BY_ID), userId);
	}

}