package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;

/**
 * ProductInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@Repository("productInfoDao")
public class ProductInfoDaoImpl extends BaseDaoImpl<ProductInfo, Long> implements ProductInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductInfoDao.";
	private final static String UPDATE_PRODUCT_CATEGORY_BY_CONDITION= "updateProductCategoryByCondition";
	private final static String SELECT_SALER_EMAIL_BY_PRODUCT_ID = "selectSalerEmailByProductId";
	private final static String SELECT_PRODUCT_INFO_BY_MESSAGE_ID = "selectProductInfoByMessageId";
	private final static String SELECT_MAIN_PIC_URL_BY_ID = "selectMainPicUrlById";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
		return this.update(getNameSpace(UPDATE_PRODUCT_CATEGORY_BY_CONDITION), productInfo);
	}

	@Override
	public String selectSalerEmailByProductId(Long id) {
		return this.select(getNameSpace(SELECT_SALER_EMAIL_BY_PRODUCT_ID), id);
	}

	@Override
	public ProductInfo selectProductInfoByMessageId(Long id) {
		return this.select(getNameSpace(SELECT_PRODUCT_INFO_BY_MESSAGE_ID), id);
	}

	@Override
	public String selectMainPicUrlById(Long id) {
		return this.select(getNameSpace(SELECT_MAIN_PIC_URL_BY_ID), id);
	}
}