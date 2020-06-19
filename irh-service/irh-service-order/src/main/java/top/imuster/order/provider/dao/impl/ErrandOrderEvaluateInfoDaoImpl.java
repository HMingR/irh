package top.imuster.order.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.order.api.pojo.ErrandOrderEvaluateInfo;
import top.imuster.order.provider.dao.ErrandOrderEvaluateInfoDao;

/**
 * ErrandOrderEvaluateInfoDao 实现类
 * @author 黄明人
 * @since 2020-06-19 19:54:16
 */
@Repository("errandOrderEvaluateInfoDao")
public class ErrandOrderEvaluateInfoDaoImpl extends BaseDaoImpl<ErrandOrderEvaluateInfo, Long> implements ErrandOrderEvaluateInfoDao {
	private final static String NAMESPACE = "top.imuster.order.provider.dao.ErrandOrderEvaluateInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}