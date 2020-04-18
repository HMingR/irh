package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.dao.ProductCategoryInfoDao;

import java.util.List;

/**
 * ProductCategoryInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@Repository("productCategoryInfoDao")
public class ProductCategoryInfoDaoImpl extends BaseDaoImpl<ProductCategoryInfo, Long> implements ProductCategoryInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductCategoryInfoDao.";
	private final static String SELECT_ALL_CATEGORY = "selectAllCategory";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ProductCategoryInfo> selectAllCategory() {
		return this.selectList(getNameSpace(SELECT_ALL_CATEGORY), null);
	}
}