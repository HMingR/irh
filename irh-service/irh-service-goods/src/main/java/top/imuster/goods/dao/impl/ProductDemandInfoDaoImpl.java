package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductDemandInfoDao;

import java.util.List;

/**
 * ProductDemandInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
@Repository("productDemandInfoDao")
public class ProductDemandInfoDaoImpl extends BaseDaoImpl<ProductDemandInfo, Long> implements ProductDemandInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductDemandInfoDao.";
	private final static String SELECT_LIST = "selectList";
	private final static String UPDATE_BROWSER_TIMES_BY_CONDITION = "updateBrowserTimesByCondition";
	private final static String SELECT_USER_ID_BY_DEMAND_ID = "selectUserIdByDemandId";
	private final static String UPDATE_COLLECT_TOTAL = "updateCollectTotal";
	private final static String SELECT_INFO_BY_IDS = "selectInfoByIds";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public void updateBrowserTimesByCondition(List<ProductInfo> update) {
		update(getNameSpace(UPDATE_BROWSER_TIMES_BY_CONDITION), update);
	}

	@Override
	public Long selectUserIdByDemandId(Long id) {
		return this.select(getNameSpace(SELECT_USER_ID_BY_DEMAND_ID), id);
	}

	@Override
	public Integer updateCollectTotal(List<GoodsForwardDto> list) {
		if(list == null || list.isEmpty()) return 0;
		return this.update(getNameSpace(UPDATE_COLLECT_TOTAL),list);
	}

	@Override
	public List<ProductDemandInfo> selectInfoByIds(List<Long> res) {
		return this.selectList(getNameSpace(SELECT_INFO_BY_IDS), res);
	}
}