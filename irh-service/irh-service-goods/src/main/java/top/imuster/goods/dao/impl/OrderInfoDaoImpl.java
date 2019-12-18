package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.OrderInfo;
import top.imuster.goods.dao.OrderInfoDao;

/**
 * OrderInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("orderInfoDao")
public class OrderInfoDaoImpl extends BaseDaoImpl<OrderInfo, Long> implements OrderInfoDao {
	private final static String NAMESPACE = "OrderInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}