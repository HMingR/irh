package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.AuthRoleRel;

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
}