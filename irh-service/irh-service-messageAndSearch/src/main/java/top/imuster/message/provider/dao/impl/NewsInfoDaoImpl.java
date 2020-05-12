package top.imuster.message.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.dao.NewsInfoDao;

import java.util.List;

/**
 * NewsInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
@Repository("newsInfoDao")
public class NewsInfoDaoImpl extends BaseDaoImpl<NewsInfo, Long> implements NewsInfoDao {
	private final static String NAMESPACE = "top.imuster.message.provider.dao.NewsInfoDao.";
	private final static String SELECT_AT_ME_MESSAGE = "selectAtMeMessage";
	private final static String SELECT_AT_ME_TOTAL = "selectAtMeTotal";
	private final static String SELECT_RECEIVER_ID_BY_ID = "selectReceiverIdById";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<NewsInfo> selectAtMeMessage(NewsInfo newsInfo) {
		return this.selectList(getNameSpace(SELECT_AT_ME_MESSAGE), newsInfo);
	}

	@Override
	public Integer selectAtMeTotal(NewsInfo newsInfo) {
		return this.select(getNameSpace(SELECT_AT_ME_TOTAL), newsInfo);
	}

	@Override
	public Long selectReceiverIdById(Long id) {
		return this.select(getNameSpace(SELECT_RECEIVER_ID_BY_ID), id);
	}
}