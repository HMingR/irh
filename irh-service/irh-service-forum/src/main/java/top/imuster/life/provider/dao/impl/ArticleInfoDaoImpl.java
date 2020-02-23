package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.dto.UserBriefDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopic;
import top.imuster.life.provider.dao.ArticleInfoDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArticleInfoDao 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Repository("articleInfoDao")
public class ArticleInfoDaoImpl extends BaseDaoImpl<ArticleInfo, Long> implements ArticleInfoDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ArticleInfoDao.";
	private final static String SELECT_LIST_BY_CONDITION = "selectListByCondition";
	private final static String SELECT_UP_TOTAL_BY_IDS = "selectUpTotalByIds";
	private final static String SELECT_UP_TOTAL_BY_ID = "selectUpTotalById";
	private final static String SELECT_BRIEF_BY_ID = "selectBriefById";
	private final static String SELECT_INFO_BY_TARGET_IDS = "selectInfoByTargetIds";
	private final static String SELECT_USER_UP_TOTAL_BY_ID = "selectUserBriefTotalById";
	private final static String SELECT_UP_TOTAL_TOP_5 = "selectUpTop5ByCategoryId";
	private final static String SELECT_BROWSER_TIMES_BY_IDS = "selectBrowserTimesByIds";
	private final static String SELECT_BROWSER_TIMES_BY_CONDITION = "updateBrowserTimesByCondition";
	private final static String SELECT_BRIEF_BY_HOT_TOPIC_ID = "selectBriefByHotTopicId";
	private final static String UPDATE_FORARDR_TIMES_BY_CONDITION = "updateForwardTimesByCondition";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<ArticleInfo> selectListByCondition(ArticleInfo articleInfo) {
		return this.selectList(getNameSpace(SELECT_LIST_BY_CONDITION), articleInfo);
	}

	@Override
	public List<ArticleInfo> selectUpTotalByIds(Long[] ids) {
		return this.selectList(getNameSpace(SELECT_UP_TOTAL_BY_IDS), ids);
	}

	@Override
	public Long selectUpTotalById(Long id) {
		return this.select(getNameSpace(SELECT_UP_TOTAL_BY_ID), id);
	}

	@Override
	public ArticleInfo selectBriefById(Long id) {
		return this.select(getNameSpace(SELECT_BRIEF_BY_ID), id);
	}

	@Override
	public List<ArticleInfo> selectInfoByTargetIds(Long[] longs) {
		return this.selectList(getNameSpace(SELECT_INFO_BY_TARGET_IDS), longs);
	}

	@Override
	public UserBriefDto selectUserBriefTotalById(Long userId) {
		return this.select(getNameSpace(SELECT_USER_UP_TOTAL_BY_ID), userId);
	}

	@Override
	public List<ArticleInfo> selectUpTop5ByCategoryId(Long id) {
		return this.selectList(getNameSpace(SELECT_UP_TOTAL_TOP_5), id);
	}

	@Override
	public Map<Long, Long> selectBrowserTimesByIds(Long[] ids) {
		HashMap<Long, Long> res = new HashMap<>();
		List<Map<Long, Long>> objects = this.selectList(getNameSpace(SELECT_BROWSER_TIMES_BY_IDS), ids);
		objects.stream().forEach(longLongMap -> {
			Long browserTimes = Long.parseLong(longLongMap.get("browser_times") + "");
			Long id = Long.parseLong(longLongMap.get("id") + "");
			res.put(id, browserTimes);
		});
		return res;
	}

	@Override
	public void updateBrowserTimesByCondition(List<ArticleInfo> update) {
		this.update(getNameSpace(SELECT_BROWSER_TIMES_BY_CONDITION), update);
	}

	@Override
	public void updateForwardTimesByCondition(List<ForwardDto> res) {
		this.insert(getNameSpace(UPDATE_FORARDR_TIMES_BY_CONDITION), res);
	}

	@Override
	public ForumHotTopic selectBriefByHotTopicId(Long aLong) {
		return this.select(getNameSpace(SELECT_BRIEF_BY_HOT_TOPIC_ID), aLong);
	}
}