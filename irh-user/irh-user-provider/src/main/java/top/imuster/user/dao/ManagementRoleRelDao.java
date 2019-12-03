package top.imuster.user.dao;

import top.imuster.domain.base.BaseDao;
import top.imuster.user.pojo.ManagementRoleRel;
import top.imuster.user.pojo.RoleInfo;

/**
 * ManagementRoleRelDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface ManagementRoleRelDao extends BaseDao<ManagementRoleRel, Long> {
    //自定义扩展
    RoleInfo selectManagementRoleInfoByManagementId(Long managementId) throws Exception;


}