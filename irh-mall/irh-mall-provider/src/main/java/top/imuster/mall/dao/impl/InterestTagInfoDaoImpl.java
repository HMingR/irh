package top.imuster.mall.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.mall.dao.InterestTagInfoDao;
import top.imuster.mall.domain.InterestTagInfo;

/**
 * InterestTagInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("interestTagInfoDao")
public class InterestTagInfoDaoImpl extends BaseDaoImpl<InterestTagInfo, Long> implements InterestTagInfoDao {
	private final static String NAMESPACE = "top.imuster.mall.dao.InterestTagInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}