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
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
		return this.update(getNameSpace(UPDATE_PRODUCT_CATEGORY_BY_CONDITION), productInfo);
	}
}