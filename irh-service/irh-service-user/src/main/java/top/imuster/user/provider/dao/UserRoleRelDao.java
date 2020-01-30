package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.api.pojo.RoleInfo;

import java.util.List;

/**
 * UserRoleRelDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface UserRoleRelDao extends BaseDao<UserRoleRel, Long> {
    //自定义扩展
    RoleInfo selectUserRoleInfoByUserId(Long userId) throws Exception;

    /**
     * @Author hmr
     * @Description 根据登录名查询用户角色
     * @Date: 2020/1/30 17:56
     * @param loginName
     * @reture: java.util.List<java.lang.String>
     **/
    List<String> selectRoleNameByUserName(String loginName);


}