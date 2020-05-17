package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.PropagateInfo;
import top.imuster.user.provider.dao.PropagateInfoDao;

/**
 * PropagateInfoDao 实现类
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
@Repository("propagateInfoDao")
public class PropagateInfoDaoImpl extends BaseDaoImpl<PropagateInfo, Long> implements PropagateInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.PropagateInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}