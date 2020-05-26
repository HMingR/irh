package top.imuster.auth.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.security.api.pojo.AuthRoleRel;

import java.util.List;

/**
 * AuthRoleRelService接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface AuthRoleRelService extends BaseService<AuthRoleRel, Long> {

    /**
     * @Author hmr
     * @Description 修改角色的权限
     * @Date: 2020/2/4 14:58
     * @param authRoleRel
     * @reture: void
     **/
    void editRoleAuth(AuthRoleRel authRoleRel);

    /**
     * @Author hmr
     * @Description 根据权限id获得角色的名称
     * @Date: 2020/5/25 15:46
     * @param id
     * @reture: java.util.List<java.lang.String>
     **/
    List<String> selectRoleNameByAuthId(Long id);
}