package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductDemandReplyInfo;
import top.imuster.goods.dao.ProductDemandReplyInfoDao;

/**
 * ProductDemandReplyInfoDao 实现类
 * @author 黄明人
 * @since 2020-05-03 15:01:34
 */
@Repository("productDemandReplyInfoDao")
public class ProductDemandReplyInfoDaoImpl extends BaseDaoImpl<ProductDemandReplyInfo, Long> implements ProductDemandReplyInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductDemandReplyInfoDao.";
	private final static String SELECT_REPLY_TOTAL_BY_DEMAND_ID = "selectReplyTotalByDemandId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Integer selectReplyTotalByDemandId(Long id) {
		return this.select(getNameSpace(SELECT_REPLY_TOTAL_BY_DEMAND_ID), id);
	}
}