package top.imuster.order.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.dao.OrderInfoDao;

import java.util.HashMap;

/**
 * OrderInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("orderInfoDao")
public class OrderInfoDaoImpl extends BaseDaoImpl<OrderInfo, Long> implements OrderInfoDao {
	private final static String NAMESPACE = "top.imuster.order.provider.dao.OrderInfoDao.";
	private final static String SELECT_ORDER_BY_ORDER_CODE = "selectOrderByOrderCode";
	private final static String INSERT_ORDER = "insertOrder";
	private final static String SELECT_AMOUNT_INCREMENT_TOTAL = "selectAmountIncrementTotal";
	private final static String SELECT_ORDER_AMOUNT_TOTAL_BY_CREATE_TIME = "selectOrderAmountTotalByCreateTime";
	private final static String SELECT_ORDER_TOTAL_BY_CREATE_TIME = "selectOrderTotalByCreateTime";
	private final static String SELECT_ORDER_CODE_VERSION_BY_CODE = "selectOrderVersionByCode";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public OrderInfo selectOrderByOrderCode(String orderCode) {
		return this.select(getNameSpace(SELECT_ORDER_BY_ORDER_CODE), orderCode);
	}

	@Override
	public Long insertOrder(OrderInfo orderInfo) {
		return (long)this.insert(getNameSpace(INSERT_ORDER), orderInfo);
	}

	@Override
	public Long selectAmountIncrementTotal(HashMap<String, String> param) {
		return this.select(getNameSpace(SELECT_AMOUNT_INCREMENT_TOTAL), param);
	}

	@Override
	public Long selectOrderAmountTotalByCreateTime(String s) {
		return this.select(getNameSpace(SELECT_ORDER_AMOUNT_TOTAL_BY_CREATE_TIME), s);
	}

	@Override
	public long selectOrderTotalByCreateTime(String s) {
		return this.select(getNameSpace(SELECT_ORDER_TOTAL_BY_CREATE_TIME), s);
	}

	@Override
	public Integer selectOrderVersionByCode(String orderCode) {
		return this.select(getNameSpace(SELECT_ORDER_CODE_VERSION_BY_CODE), orderCode);
	}
}