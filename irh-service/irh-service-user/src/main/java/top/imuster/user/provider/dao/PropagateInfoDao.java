package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.PropagateInfo;

import java.util.List;

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

}