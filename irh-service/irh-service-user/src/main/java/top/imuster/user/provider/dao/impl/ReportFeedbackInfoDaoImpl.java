package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.dao.ReportFeedbackInfoDao;

import java.util.List;

/**
 * ReportFeedbackInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
@Repository("reportFeedbackInfoDao")
public class ReportFeedbackInfoDaoImpl extends BaseDaoImpl<ReportFeedbackInfo, Long> implements ReportFeedbackInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.ReportFeedbackInfoDao.";
	private final static String SELECT_STATISTICS_BY_CONDITION = "selectStatisticsByCondition";

	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ReportFeedbackInfo> selectStatisticsByCondition(ReportFeedbackInfo condition) {
		return selectList(getNameSpace(SELECT_STATISTICS_BY_CONDITION), condition);
	}
}