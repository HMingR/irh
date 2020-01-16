package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.dao.ProductDemandInfoDao;

/**
 * ProductDemandInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
@Repository("productDemandInfoDao")
public class ProductDemandInfoDaoImpl extends BaseDaoImpl<ProductDemandInfo, Long> implements ProductDemandInfoDao {
	private final static String NAMESPACE = "top.imuster.user.api.pojo.dao.ProductDemandInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}