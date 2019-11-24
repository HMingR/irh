package top.imuster.mall.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.mall.dao.OrderPaymentInfoDao;
import top.imuster.mall.domain.OrderPaymentInfo;

/**
 * OrderPaymentInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("orderPaymentInfoDao")
public class OrderPaymentInfoDaoImpl extends BaseDaoImpl<OrderPaymentInfo, Long> implements OrderPaymentInfoDao {
	private final static String NAMESPACE = "top.imuster.mall.dao.OrderPaymentInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}