package top.imuster.user.service;

import top.imuster.service.base.BaseService;
import top.imuster.user.pojo.ManagementInfo;

/**
 * ManagementInfoService接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface ManagementInfoService extends BaseService<ManagementInfo, Long> {

    /**
     * @Description: 根据管理员的名称查询管理员的所有信息,包括权限信息
     * @Author: hmr
     * @Date: 2019/12/2 16:53
     * @param name
     * @reture: top.imuster.user.pojo.ManagementInfo
     **/
    ManagementInfo getManagementRoleByCondition(ManagementInfo condition) throws Exception;

    /**
     * @Description: 根据管理员的姓名登录
     * @Author: hmr
     * @Date: 2019/12/2 17:14
     * @param name
     * @reture: top.imuster.user.pojo.ManagementInfo
     **/
    ManagementInfo managementLoginByName(String name) throws Exception;
}