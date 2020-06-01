package top.imuster.user.provider.dao;


import org.apache.ibatis.annotations.Param;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.user.api.pojo.PropagateInfo;

import java.util.List;
import java.util.Map;

/**
 * PropagateInfoDao 接口
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
public interface PropagateInfoDao extends BaseDao<PropagateInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 分页获得简略信息
     * @Date: 2020/5/25 10:32
     * @param condition
     * @reture: java.util.List<top.imuster.user.api.pojo.PropagateInfo>
     **/
    List<PropagateInfo> selectBriefInfoList(PropagateInfo condition);

    /**
     * @Author hmr
     * @Description 更新浏览次数
     * @Date: 2020/5/31 15:22
     * @param mapFromRedis
     * @reture: java.lang.Integer
     **/
    Integer updateBrowseTimesByMap(@Param("map") Map<Long, Integer> mapFromRedis);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/6/1 20:39
     * @param res
     * @reture: java.lang.Integer
     **/
    Integer updateBrowseTimes(List<BrowserTimesDto> res);
}