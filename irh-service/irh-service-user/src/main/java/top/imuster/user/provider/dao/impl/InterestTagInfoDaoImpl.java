package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.InterestTagInfo;
import top.imuster.user.provider.dao.InterestTagInfoDao;

/**
 * InterestTagInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("interestTagInfoDao")
public class InterestTagInfoDaoImpl extends BaseDaoImpl<InterestTagInfo, Long> implements InterestTagInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.InterestTagInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}