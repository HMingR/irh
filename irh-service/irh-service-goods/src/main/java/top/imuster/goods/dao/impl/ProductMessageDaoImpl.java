
package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductMessageInfo;
import top.imuster.goods.dao.ProductMessageDao;

/**
 * ProductMessageDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@Repository("productMessageDao")
public class ProductMessageDaoImpl extends BaseDaoImpl<ProductMessageInfo, Long> implements ProductMessageDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductMessageDao.";
	private final static String SELECT_USER_ID_BY_MESSAGE_PARENT_ID = "selectUserIdByMessageParentId";
	private final static String SELECT_REPLY_TOTAL_BY_ID = "selectReplyTotalById";
	private final static String SELECT_PARENT_WRITER_ID_BY_ID = "selectParentWriterIdById";
	private final static String SELECT_LIST_BY_CONDITION = "selectListByCondition";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long selectUserIdByMessageParentId(Long parentId) {
		return this.select(getNameSpace(SELECT_USER_ID_BY_MESSAGE_PARENT_ID), parentId);
	}

	@Override
	public Integer selectReplyTotalById(Long id) {
		return this.select(getNameSpace(SELECT_REPLY_TOTAL_BY_ID), id);
	}

	@Override
	public Long selectParentWriterIdById(Long pid) {
		return this.select(getNameSpace(SELECT_PARENT_WRITER_ID_BY_ID), pid);
	}

}