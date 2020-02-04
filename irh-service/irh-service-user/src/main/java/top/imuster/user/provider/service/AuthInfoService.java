package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.AuthInfo;

import java.util.List;

/**
 * AuthInfoService接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface AuthInfoService extends BaseService<AuthInfo, Long> {

    /**
     * @Description: 完整的删除权限，删除所有和这条记录有关的信息(删除角色的权限,删除角色关联表中的有关信息)
     *               该操作需要有事务的控制，并且传播行为为REQUIRED
     * @Author: hmr
     * @Date: 2019/12/17 20:22
     * @param authId
     * @reture: int
     **/
    void deleteAuthById(Long authId);

    /**
     * @Author hmr
     * @Description 根据角色id生成权限树
     * @Date: 2020/2/4 13:55
     * @param roleId 角色id
     * @reture: java.util.List<top.imuster.user.api.pojo.AuthInfo>
     **/
    List<AuthInfo> tree(Long roleId);
}