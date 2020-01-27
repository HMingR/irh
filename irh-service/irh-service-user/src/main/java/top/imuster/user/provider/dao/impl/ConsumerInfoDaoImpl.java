package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.dao.ConsumerInfoDao;

/**
 * ConsumerInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("consumerInfoDao")
public class ConsumerInfoDaoImpl extends BaseDaoImpl<UserInfo, Long> implements ConsumerInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.ConsumerInfoDao.";
	private final static String CHECK_INFO = "checkInfo";
	private final static String SELECT_EMAIL_BY_ID = "selectEmailById";

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
}