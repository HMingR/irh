package top.imuster.message.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.dao.NewsInfoDao;

import java.util.HashMap;
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
	private final static String SELECT_IDS_BY_RESOURCE_ID = "selectIdsByResourceId";
	private final static String UPDATE_STATE_BY_SOURCE_ID = "updateStateBySourceId";
	private final static String SELECT_SYSTEM_UNREAD_TOTAL = "selectSystemUnreadTotal";
	private final static String SELECT_AT_ME_UNREAD_TOTAL = "selectAtMeUnreadTotal";
	private final static String UPDATE_STATE_BY_USER_ID = "updateStateByUserId";
	private final static String SELECT_SYSTEM_NEWS_TOTAL_BY_USER_ID = "selectSystemNewsTotalByUserId";
	private final static String SELECT_SYSTEM_NEWS_BY_PAGE = "selectSystemNewsByPage";
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

	@Override
	public Integer updateStateBySourceId(NewsInfo newsInfo) {
		return this.update(getNameSpace(UPDATE_STATE_BY_SOURCE_ID), newsInfo);
	}

	@Override
	public Integer selectSystemUnreadTotal(NewsInfo newsInfo) {
		return this.select(getNameSpace(SELECT_SYSTEM_UNREAD_TOTAL), newsInfo);
	}

	@Override
	public Integer selectAtMeUnreadTotal(NewsInfo newsInfo) {
		return this.select(getNameSpace(SELECT_AT_ME_UNREAD_TOTAL), newsInfo);
	}

	@Override
	public Integer updateStateByUserId(HashMap<String, String> param) {
		return this.update(getNameSpace(UPDATE_STATE_BY_USER_ID), param);
	}

	@Override
	public Integer selectSystemNewsTotalByUserId(Long userId) {
		return this.select(getNameSpace(SELECT_SYSTEM_NEWS_TOTAL_BY_USER_ID), userId);
	}

	@Override
	public List<NewsInfo> selectSystemNewsByPage(NewsInfo newsInfo) {
		return this.selectList(getNameSpace(SELECT_SYSTEM_NEWS_BY_PAGE), newsInfo);
	}
}