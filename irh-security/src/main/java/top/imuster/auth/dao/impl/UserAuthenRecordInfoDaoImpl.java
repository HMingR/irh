package top.imuster.auth.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.auth.dao.UserAuthenRecordInfoDao;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.security.api.pojo.UserAuthenRecordInfo;

/**
 * UserAuthenRecordInfoDao 实现类
 * @author 黄明人
 * @since 2020-03-27 15:53:30
 */
@Repository("userAuthenRecordInfoDao")
public class UserAuthenRecordInfoDaoImpl extends BaseDaoImpl<UserAuthenRecordInfo, Long> implements UserAuthenRecordInfoDao {
	private final static String NAMESPACE = "top.imuster.auth.dao.UserAuthenRecordInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}