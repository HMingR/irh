package top.imuster.life.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.life.api.pojo.ErrandInfo;

import java.util.List;

/**
 * ErrandInfoDao 接口
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
public interface ErrandInfoDao extends BaseDao<ErrandInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 查询自己发布的服务
     * @Date: 2020/2/11 19:48
     * @param searchCondition
     * @reture: java.util.List<top.imuster.life.api.pojo.ErrandInfo>
     **/
    List<ErrandInfo> selectList(ErrandInfo searchCondition);

    /**
     * @Author hmr
     * @Description 根据id查看state
     * @Date: 2020/2/12 11:21
     * @param id
     * @reture: java.lang.Integer
     **/
    Integer selectStateById(Long id);

    /**
     * @Author hmr
     * @Description 根据用户id查看该用户发布的服务数量
     * @Date: 2020/4/23 19:38
     * @param condition
     * @reture: java.lang.Integer
     **/
    Integer selectListCountByUserId(Long userId);
}