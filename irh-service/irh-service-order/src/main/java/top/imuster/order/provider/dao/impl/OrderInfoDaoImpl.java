package top.imuster.order.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.dao.OrderInfoDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final static String SELECT_ORDER_LIST_COUNT_BY_USER_ID = "selectOrderListCountByUserId";
	private final static String SELECT_ORDER_LIST_BY_USER_ID= "selectOrderListByUserId";
	private final static String SELECT_ALL_DONATION_ORDER = "selectAllDonationOrder";
	private final static String SELECT_ORDER_VERSION_BY_ID = "selectOrderVersionById";
	private final static String COMPLETE_TRADE = "completeTrade";
	private final static String SELECT_PRODUCT_ID_BY_ORDER_CODE = "selectProductIdAndBuyerIdByOrderCode";
	private final static String UPDATE_ORDER_STATE_BY_ORDER_CODE = "updateOrderStateByOrderCode";
	private final static String GET_BUYER_ID_BY_ORDER_CODE = "getBuyerIdByOrderCode";
	private final static String SELECT_DONATION_COUNT = "selectDonationCount";
	private final static String SELECT_DONATION_LIST_BY_CONDITION = "selectDonationListByCondition";
	private final static String UPDATE_ORDER_STATE_BY_ID = "updateOrderStateById";
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
	public Long selectOrderTotalByCreateTime(String s) {
		return this.select(getNameSpace(SELECT_ORDER_TOTAL_BY_CREATE_TIME), s);
	}

	@Override
	public Integer selectOrderVersionByCode(String orderCode) {
		return this.select(getNameSpace(SELECT_ORDER_CODE_VERSION_BY_CODE), orderCode);
	}

	@Override
	public Integer selectOrderListCountByUserId(OrderInfo orderInfo) {
		return this.select(getNameSpace(SELECT_ORDER_LIST_COUNT_BY_USER_ID), orderInfo);
	}

	@Override
	public List<OrderInfo> selectOrderListByUserId(OrderInfo orderInfo) {
		return this.selectList(getNameSpace(SELECT_ORDER_LIST_BY_USER_ID), orderInfo);
	}

	@Override
	public List<OrderInfo> selectAllDonationOrder() {
		return this.selectList(getNameSpace(SELECT_ALL_DONATION_ORDER), null);
	}

	@Override
	public Integer selectOrderVersionById(Long id) {
		return this.select(getNameSpace(SELECT_ORDER_VERSION_BY_ID), id);
	}

	@Override
	public Integer completeTrade(OrderInfo orderInfo) {
		return this.update(getNameSpace(COMPLETE_TRADE), orderInfo);
	}

	@Override
	public Map<String, String> selectProductIdAndBuyerIdByOrderCode(String orderCode) {
		return this.select(getNameSpace(SELECT_PRODUCT_ID_BY_ORDER_CODE), orderCode);
	}

	@Override
	public Integer updateOrderStateByOrderCode(OrderInfo info) {
		return this.update(getNameSpace(UPDATE_ORDER_STATE_BY_ORDER_CODE), info);
	}

	@Override
	public Integer selectDonationCount() {
		return this.select(getNameSpace(SELECT_DONATION_COUNT), null);
	}

	@Override
	public List<OrderInfo> selectDonationListByCondition(OrderInfo orderInfo) {
		return this.selectList(getNameSpace(SELECT_DONATION_LIST_BY_CONDITION), orderInfo);
	}

	@Override
	public Integer updateOrderStateById(HashMap<String, String> param) {
		return this.update(getNameSpace(UPDATE_ORDER_STATE_BY_ID), param);
	}

}