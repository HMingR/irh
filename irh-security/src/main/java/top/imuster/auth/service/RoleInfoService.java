package top.imuster.auth.service;

import top.imuster.common.base.service.BaseService;
import top.imuster.security.api.pojo.RoleInfo;

import java.util.List;

/**
 * RoleInfoService接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface RoleInfoService extends BaseService<RoleInfo, Long> {

    /**
     * @Description: 删除角色以及和该角色相关的信息(权限角色表和用户角色表中的信息)
     * @Author: hmr
     * @Date: 2019/12/18 10:23
     * @param roleId
     * @reture:
     **/
    void deleteById(Long roleId);

    /**
     * @Description: 查询角色对应的所有可用权限
     * @Author: hmr
     * @Date: 2019/12/18 12:31
     * @param roleId
     * @reture: top.imuster.security.api.pojo.RoleInfo
     **/
    RoleInfo getRoleAndAuthByRoleId(Long roleId);

    /**
     * @Author hmr
     * @Description 根据roleName获得role的信息，包括权限信息
     * @Date: 2020/1/30 16:30
     * @param name
     * @reture: top.imuster.user.api.pojo.RoleInfo
     **/
    RoleInfo getRoleAndAuthByRoleName(String name);

    /**
     * @Description: 获得所有的角色和对应的权限
     * @Author: hmr
     * @Date: 2019/12/24 16:07
     * @param
     * @reture: java.util.List<top.imuster.security.api.pojo.RoleInfo>
     **/
    List<RoleInfo> getRoleAndAuthList();

    /**
     * @Author hmr
     * @Description 根据管理员id获得该管理员没有拥有的角色
     * @Date: 2020/1/22 10:17
     * @param adminId
     * @reture: java.util.ArrayList<top.imuster.security.api.pojo.RoleInfo>
     *
     * @return*/
    List<RoleInfo> getOtherRoleByAdminId(Long adminId);

    /**
     * @Author hmr
     * @Description 通过登录名查询用户的角色名称
     * @Date: 2020/1/30 17:54
     * @param loginName
     * @reture: java.util.List<java.lang.String>
     **/
    List<String> getRoleNameByUserName(String loginName);
}