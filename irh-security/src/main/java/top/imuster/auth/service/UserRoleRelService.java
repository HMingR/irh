package top.imuster.auth.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.security.api.pojo.UserRoleRel;

import java.util.List;

/**
 * ManagementRoleRelService接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface UserRoleRelService extends BaseService<UserRoleRel, Long> {
    /**
     * @Description: 根据条件判断是否有满足条件的实体
     * @Author: hmr
     * @Date: 2019/12/26 20:02
     * @param userRoleRel
     * @reture: java.lang.Integer
     **/
    Integer getCountByCondition(UserRoleRel userRoleRel);

    /**
     * @Author hmr
     * @Description 根据登录名获得用户角色
     * @Date: 2020/1/30 18:05
     * @param loginName
     * @reture: java.util.List<java.lang.String>
     **/
    List<String> getRoleNameByUserName(String loginName);
}