package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.ManagementRoleRel;

/**
 * ManagementRoleRelService接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface ManagementRoleRelService extends BaseService<ManagementRoleRel, Long> {
    /**
     * @Description: 根据条件判断是否有满足条件的实体
     * @Author: hmr
     * @Date: 2019/12/26 20:02
     * @param managementRoleRel
     * @reture: java.lang.Integer
     **/
    Integer getCountByCondition(ManagementRoleRel managementRoleRel);
}