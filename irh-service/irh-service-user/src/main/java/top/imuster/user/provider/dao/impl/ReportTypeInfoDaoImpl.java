package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.ReportTypeInfo;
import top.imuster.user.provider.dao.ReportTypeInfoDao;

/**
 * ReportTypeInfoDao 实现类
 * @author 黄明人
 * @since 2020-05-25 10:03:10
 */
@Repository("reportTypeInfoDao")
public class ReportTypeInfoDaoImpl extends BaseDaoImpl<ReportTypeInfo, Long> implements ReportTypeInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.ReportTypeInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}