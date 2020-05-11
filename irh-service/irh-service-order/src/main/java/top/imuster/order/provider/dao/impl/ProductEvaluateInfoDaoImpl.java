package top.imuster.order.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.order.api.pojo.ProductEvaluateInfo;
import top.imuster.order.provider.dao.ProductEvaluateInfoDao;

/**
 * ProductEvaluateInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("productEvaluateInfoDao")
public class ProductEvaluateInfoDaoImpl extends BaseDaoImpl<ProductEvaluateInfo, Long> implements ProductEvaluateInfoDao {
	private final static String NAMESPACE = "top.imuster.order.provider.dao.ProductEvaluateInfoDao.";
	private final static String SELECT_ID_BY_ORDER_ID = "selectIdByOrderId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long selectIdByOrderId(Long id) {
		return this.select(getNameSpace(SELECT_ID_BY_ORDER_ID), id);
	}
}