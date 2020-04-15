package top.imuster.order.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.provider.dao.ProductDonationApplyInfoDao;

/**
 * ProductDonationApplyInfoDao 实现类
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
@Repository("productDonationApplyInfoDao")
public class ProductDonationApplyInfoDaoImpl extends BaseDaoImpl<ProductDonationApplyInfo, Long> implements ProductDonationApplyInfoDao {
	private final static String NAMESPACE = "top.imuster.order.provider.dao.ProductDonationApplyInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}