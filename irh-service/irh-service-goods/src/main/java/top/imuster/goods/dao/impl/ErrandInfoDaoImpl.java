package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.dao.ErrandInfoDao;
import top.imuster.life.api.pojo.ErrandInfo;

import java.util.List;

/**
 * ErrandInfoDao 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
@Repository("errandInfoDao")
public class ErrandInfoDaoImpl extends BaseDaoImpl<ErrandInfo, Long> implements ErrandInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ErrandInfoDao.";
	private final static String SELECT_LIST = "selectList";
	private final static String SELECT_STATE_BY_ID_AND_VERSION = "selectStateByIdAndVersion";
	private final static String SELECT_LIST_COUNT_BY_USER_ID = "selectListCountByUserId";
	private final static String UPDATE_STATE_BY_ID_AND_VERSION = "updateStateByIdAndVersion";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ErrandInfo> selectList(ErrandInfo searchCondition) {
		return this.selectList(getNameSpace(SELECT_LIST), searchCondition);
	}

	@Override
	public Integer selectStateByIdAndVersion(ErrandInfo errandInfo) {
		return this.select(getNameSpace(SELECT_STATE_BY_ID_AND_VERSION), errandInfo);
	}

	@Override
	public Integer selectListCountByUserId(Long userId) {
		return this.select(getNameSpace(SELECT_LIST_COUNT_BY_USER_ID), userId);
	}

	@Override
	public Integer updateStateByIdAndVersion(ErrandInfo errandInfo) {
		return this.update(getNameSpace(UPDATE_STATE_BY_ID_AND_VERSION), errandInfo);
	}
}