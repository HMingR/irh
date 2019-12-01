package top.imuster.item.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.item.dao.InterestTagInfoDao;
import top.imuster.item.domain.InterestTagInfo;

/**
 * InterestTagInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("interestTagInfoDao")
public class InterestTagInfoDaoImpl extends BaseDaoImpl<InterestTagInfo, Long> implements InterestTagInfoDao {
	private final static String NAMESPACE = "top.imuster.item.dao.InterestTagInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}