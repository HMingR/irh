package top.imuster.user.provider.dao;


import org.apache.ibatis.annotations.Param;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.RoleInfo;

import java.util.List;

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

    /**
     * @Description: 获得所有角色和对应的权限
     * @Author: hmr
     * @Date: 2019/12/24 16:10
     * @param
     * @reture: java.util.List<top.imuster.user.api.pojo.RoleInfo>
     **/
    List<RoleInfo> selectRoleAndAuth();

    /**
     * @Author hmr
     * @Description 根据管理员id获得该管理员不拥有的角色信息
     * @Date: 2020/1/22 10:18
     * @param adminId
     * @reture: java.util.ArrayList<top.imuster.user.api.pojo.RoleInfo>
     *
     * @return*/
    List<RoleInfo> selectOtherRoleByAdminId(Long adminId);
}