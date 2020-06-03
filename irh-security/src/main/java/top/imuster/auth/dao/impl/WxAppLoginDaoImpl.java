package top.imuster.auth.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.auth.dao.WxAppLoginDao;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.security.api.pojo.WxAppLoginInfo;

/**
 * WxAppLoginDao 实现类
 * @author 黄明人
 * @since 2020-05-19 15:28:28
 */
@Repository("wxAppLoginDao")
public class WxAppLoginDaoImpl extends BaseDaoImpl<WxAppLoginInfo, Long> implements WxAppLoginDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.WxAppLoginDao.";
	private final static String SELECT_USER_ID_BY_OPEN_ID = "selectUserIdByOpenId";
	private final static String UPDATE_INFO_STATE_BY_USER_ID = "updateInfoStateByUserId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long selectUserIdByOpenId(String openId) {
		return this.select(getNameSpace(SELECT_USER_ID_BY_OPEN_ID), openId);
	}

	@Override
	public Integer updateInfoStateByUserId(Long userId) {
		return this.update(getNameSpace(UPDATE_INFO_STATE_BY_USER_ID), userId);
	}
}