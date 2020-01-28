package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.api.pojo.RoleInfo;

/**
 * UserRoleRelDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface UserRoleRelDao extends BaseDao<UserRoleRel, Long> {
    //自定义扩展
    RoleInfo selectUserRoleInfoByUserId(Long userId) throws Exception;


}