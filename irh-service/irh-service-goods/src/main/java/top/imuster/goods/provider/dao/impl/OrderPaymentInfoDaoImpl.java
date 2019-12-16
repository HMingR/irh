package top.imuster.goods.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.OrderPaymentInfo;
import top.imuster.goods.provider.dao.OrderPaymentInfoDao;

/**
 * OrderPaymentInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("orderPaymentInfoDao")
public class OrderPaymentInfoDaoImpl extends BaseDaoImpl<OrderPaymentInfo, Long> implements OrderPaymentInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.provider.dao.OrderPaymentInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}