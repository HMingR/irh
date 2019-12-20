package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.RoleInfo;

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
     * @reture: top.imuster.user.api.pojo.RoleInfo
     **/
    RoleInfo getRoleAndAuthByRoleId(Long roleId);

}