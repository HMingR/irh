package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.provider.dao.ExamineRecordInfoDao;

/**
 * ExamineRecordInfoDao 实现类
 * @author 黄明人
 * @since 2020-05-21 19:27:46
 */
@Repository("examineRecordInfoDao")
public class ExamineRecordInfoDaoImpl extends BaseDaoImpl<ExamineRecordInfo, Long> implements ExamineRecordInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.ExamineRecordInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}