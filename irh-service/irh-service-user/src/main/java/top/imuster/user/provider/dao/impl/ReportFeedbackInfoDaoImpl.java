package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.ReportFeedbackInfo;
import top.imuster.user.provider.dao.ReportFeedbackInfoDao;

import java.util.HashMap;
import java.util.List;

/**
 * ReportFeedbackInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
@Repository("reportFeedbackInfoDao")
public class ReportFeedbackInfoDaoImpl extends BaseDaoImpl<ReportFeedbackInfo, Long> implements ReportFeedbackInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.ReportFeedbackInfoDao.";
	private final static String SELECT_LIST_BY_CONDITION = "selectListByCondition";
	private final static String UPDATE_BY_TARGET_ID = "updateByTargetId";
	private final static String SELECT_ALL_REPORT_BY_TARGETID = "selectAllReportByTargetId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ReportFeedbackInfo> selectListByCondition(ReportFeedbackInfo condition) {
		return this.selectList(getNameSpace(SELECT_LIST_BY_CONDITION), condition);
	}

	@Override
	public void updateByTargetId(ReportFeedbackInfo condition) {
		 this.update(getNameSpace(UPDATE_BY_TARGET_ID), condition);
	}

	@Override
	public List<ReportFeedbackInfo> selectAllReportByTargetId(HashMap<String, String> params) {
		return this.selectList(getNameSpace(SELECT_ALL_REPORT_BY_TARGETID), params);
	}
}