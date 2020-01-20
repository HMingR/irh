package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.bo.ManagementDetails;
import top.imuster.user.api.pojo.ManagementInfo;

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
    ManagementInfo getManagementRoleByCondition(ManagementInfo condition);

    /**
     * @Description: 根据管理员的姓名加载信息
     * @Author: hmr
     * @Date: 2019/12/2 17:14
     * @param name
     * @reture: top.imuster.user.pojo.ManagementInfo
     **/
    ManagementDetails loadManagementByName(String name) throws Exception;

    /**
     * @Description: 管理员登录用
     * @Author: hmr
     * @Date: 2019/12/7 10:20
     * @param name
     * @param password
     * @reture: String   返回一个token
     **/
    ManagementDetails login(String name, String password) throws Exception;

    /**
     * @Description: 根据管理员的id修改管理员的角色
     * @Author: hmr
     * @Date: 2019/12/26 19:57
     * @param managementId
     * @param roleIds
     * @reture: void
     **/
    void editManagementRoleById(Long managementId, String roleIds) throws Exception;
}