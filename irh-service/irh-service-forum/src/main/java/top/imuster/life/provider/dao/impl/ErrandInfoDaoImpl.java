package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ErrandInfo;
import top.imuster.life.provider.dao.ErrandInfoDao;

import java.util.List;

/**
 * ErrandInfoDao 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
@Repository("errandInfoDao")
public class ErrandInfoDaoImpl extends BaseDaoImpl<ErrandInfo, Long> implements ErrandInfoDao {
	private final static String NAMESPACE = "top.imuster.order.provider.dao.ErrandInfoDao.";
	private final static String SELECT_LIST = "selectList";
	private final static String SELECT_STATE_BY_ID = "selectStateById";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ErrandInfo> selectList(ErrandInfo searchCondition) {
		return this.selectList(getNameSpace(SELECT_LIST), searchCondition);
	}

	@Override
	public Integer selectStateById(Long id) {
		return this.select(getNameSpace(SELECT_STATE_BY_ID), id);
	}
}