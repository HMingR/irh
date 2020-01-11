package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.dao.ReportFeedbackInfoDao;

/**
 * ReportFeedbackInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
@Repository("reportFeedbackInfoDao")
public class ReportFeedbackInfoDaoImpl extends BaseDaoImpl<ReportFeedbackInfo, Long> implements ReportFeedbackInfoDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.ReportFeedbackInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}