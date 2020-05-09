package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductCollectRel;
import top.imuster.goods.dao.ProductCollectRelDao;

/**
 * ProductCollectRelDao 实现类
 * @author 黄明人
 * @since 2020-04-26 15:48:44
 */
@Repository("productCollectRelDao")
public class ProductCollectRelDaoImpl extends BaseDaoImpl<ProductCollectRel, Long> implements ProductCollectRelDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductCollectRelDao.";
	private final static String  SELECT_USER_ID_BY_ID  = "selectUserIdById";
	private final static String DELETE_ALL_COLLECT_BY_USER_ID = "deleteAllCollectByUserId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long selectUserIdById(Long id) {
		return this.select(getNameSpace(SELECT_USER_ID_BY_ID), id);
	}

	@Override
	public Integer deleteAllCollectByUserId(Long currentUserIdFromCookie) {
		return this.update(getNameSpace(DELETE_ALL_COLLECT_BY_USER_ID), currentUserIdFromCookie);
	}
}