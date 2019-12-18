package top.imuster.user.provider.dao;


import org.apache.ibatis.annotations.Param;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.RoleInfo;

/**
 * RoleInfoDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface RoleInfoDao extends BaseDao<RoleInfo, Long> {
    //自定义扩展

    /**
     * @Description: 根据角色的id查询角色所有的权限
     * @Author: hmr
     * @Date: 2019/12/18 14:51
     * @param roleId
     * @reture: top.imuster.user.api.pojo.RoleInfo
     **/
    RoleInfo selectRoleAndAuthByRoleId(@Param("roleId") Long roleId);

}