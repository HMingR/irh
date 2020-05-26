package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.dto.UserGoodsCenterDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;

import java.util.List;
import java.util.Map;

/**
 * ProductInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@Repository("productInfoDao")
public class ProductInfoDaoImpl extends BaseDaoImpl<ProductInfo, Long> implements ProductInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductInfoDao.";
	private final static String UPDATE_PRODUCT_CATEGORY_BY_CONDITION= "updateProductCategoryByCondition";
	private final static String SELECT_SALER_ID_BY_PRODUCT_ID = "selectSalerIdByProductId";
	private final static String SELECT_PRODUCT_INFO_BY_MESSAGE_ID = "selectProductInfoByMessageId";
	private final static String SELECT_PRODUCT_BRIEF_INFO_BY_ID = "selectProductBriefInfoById";
	private final static String SELECT_BROWSER_TIMES_BY_IDS = "selectBrowserTimesByIds";
	private final static String UPDATE_BROWSER_TIMES_BY_CONDITION =  "updateBrowserTimesByCondition";
	private final static String SELECT_USER_ID_BY_PRODUCT_ID = "selectUserIdByProductId";
	private final static String UPDATE_COLLECT_TOTAL = "updateCollectTotal";
	private final static String LOCK_PRODUCT_BY_ID = "lockProductById";
	private final static String SELECT_PRODUCT_BRIEF_INFO_LIST = "selectProductBriefInfoList";
	private final static String UPDATE_PRODUCT_STATE_BY_ID = "updateProductStateById";
	private final static String SELECT_PRODUCT_BRIEF_INFO_BY_IDS = "selectProductBriefInfoByIds";
	private final static String SELECT_GOODS_BROWSE_TOTAL_BY_USER_ID = "selectGoodsBrowseTotalByUserId";
	private final static String SELECT_DONATION_MONEY_BU_USER_ID = "selectDonationMoneyByUserId";
	private final static String SELECT_SALE_TOTAL_BY_USER_ID = "selectSaleTotalByUserId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
		return this.update(getNameSpace(UPDATE_PRODUCT_CATEGORY_BY_CONDITION), productInfo);
	}

	@Override
	public Long selectSalerIdByProductId(Long id) {
		return this.select(getNameSpace(SELECT_SALER_ID_BY_PRODUCT_ID), id);
	}

	@Override
	public ProductInfo selectProductInfoByMessageId(Long id) {
		return this.select(getNameSpace(SELECT_PRODUCT_INFO_BY_MESSAGE_ID), id);
	}

	@Override
	public ProductInfo selectProductBriefInfoById(Long id) {
		return this.select(getNameSpace(SELECT_PRODUCT_BRIEF_INFO_BY_ID), id);
	}

	@Override
	public Map<Long, Long> selectBrowserTimesByIds(Long[] ids) {
		return this.select(getNameSpace(SELECT_BROWSER_TIMES_BY_IDS), ids);
	}

	@Override
	public void updateBrowserTimesByCondition(List<ProductInfo> update) {
		this.update(getNameSpace(UPDATE_BROWSER_TIMES_BY_CONDITION), update);
	}

	@Override
	public Long selectUserIdByProductId(Long id) {
		return this.select(getNameSpace(SELECT_USER_ID_BY_PRODUCT_ID), id);
	}

	@Override
	public Integer updateCollectTotal(List<GoodsForwardDto> list) {
		if(list == null || list.isEmpty()) return 0;
		return this.update(getNameSpace(UPDATE_COLLECT_TOTAL), list);
	}

	@Override
	public Integer lockProductById(Map<String, String> param) {
		return this.update(getNameSpace(LOCK_PRODUCT_BY_ID), param);
	}

	@Override
	public List<ProductInfo> selectProductBriefInfoList(ProductInfo condition) {
		return this.selectList(getNameSpace(SELECT_PRODUCT_BRIEF_INFO_LIST), condition);
	}

	@Override
	public Integer updateProductStateById(ProductInfo condition) {
		return this.update(getNameSpace(UPDATE_PRODUCT_STATE_BY_ID), condition);
	}

	@Override
	public List<ProductInfo> selectProductBriefInfoByIds(List<Long> ids) {
		return this.selectList(getNameSpace(SELECT_PRODUCT_BRIEF_INFO_BY_IDS), ids);
	}

	@Override
	public UserGoodsCenterDto selectGoodsBrowseTotalByUserId(Long id) {
		return this.select(getNameSpace(SELECT_GOODS_BROWSE_TOTAL_BY_USER_ID), id);
	}

	@Override
	public String selectDonationMoneyByUserId(Long id) {
		return this.select(getNameSpace(SELECT_DONATION_MONEY_BU_USER_ID), id);
	}

	@Override
	public Integer selectSaleTotalByUserId(Long id) {
		return this.select(getNameSpace(SELECT_SALE_TOTAL_BY_USER_ID), id);
	}
}