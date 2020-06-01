package top.imuster.user.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.user.api.pojo.PropagateInfo;
import top.imuster.user.provider.dao.PropagateInfoDao;

import java.util.List;
import java.util.Map;

/**
 * PropagateInfoDao 实现类
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
@Repository("propagateInfoDao")
public class PropagateInfoDaoImpl extends BaseDaoImpl<PropagateInfo, Long> implements PropagateInfoDao {
	private final static String NAMESPACE = "top.imuster.user.provider.dao.PropagateInfoDao.";
	private static final String SELECT_BRIEF_INFO_LIST = "selectBriefInfoList";
	private static final String UPDATE_BROWSE_TIMES_BY_MAP = "updateBrowseTimesByMap";
	private static final String UPDATE_BROWSE_TIMES = "updateBrowseTimes";

	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<PropagateInfo> selectBriefInfoList(PropagateInfo condition) {
		return this.selectList(getNameSpace(SELECT_BRIEF_INFO_LIST), condition);
	}

	@Override
	public Integer updateBrowseTimesByMap(Map<Long, Integer> mapFromRedis) {
		return this.update(getNameSpace(UPDATE_BROWSE_TIMES_BY_MAP), mapFromRedis);
	}

	@Override
	public Integer updateBrowseTimes(List<BrowserTimesDto> res) {
		return this.update(getNameSpace(UPDATE_BROWSE_TIMES), res);
	}
}