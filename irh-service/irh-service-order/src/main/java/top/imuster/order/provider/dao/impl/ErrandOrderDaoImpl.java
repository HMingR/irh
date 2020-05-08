package top.imuster.order.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.order.api.pojo.ErrandOrderInfo;
import top.imuster.order.provider.dao.ErrandOrderDao;

/**
 * ErrandOrderDao 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
@Repository("errandOrderDao")
public class ErrandOrderDaoImpl extends BaseDaoImpl<ErrandOrderInfo, Long> implements ErrandOrderDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ErrandOrderDao.";
	private final static String SELECT_ORDER_STATE_BY_CODE = "selectOrderStateByCode";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Integer selectOrderStateByCode(String code) {
		return this.select(getNameSpace(SELECT_ORDER_STATE_BY_CODE), code);
	}

}