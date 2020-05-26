package top.imuster.auth.dao;

import top.imuster.common.base.dao.BaseDao;
import top.imuster.security.api.pojo.AuthInfo;
import top.imuster.security.api.pojo.AuthRoleRel;

import java.util.List;

/**
 * AuthRoleRelDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface AuthRoleRelDao extends BaseDao<AuthRoleRel, Long> {
    //自定义扩展

    /**
     * @Description: 根据角色id查询所有权限
     * @Author: hmr
     * @Date: 2019/12/18 15:34
     * @param id
     * @reture: java.util.List<top.imuster.user.api.pojo.AuthInfo>
     **/
    List<AuthInfo> selectAuthListByRoleId(Long id);

    /**
     * @Author hmr
     * @Description 根据条件删除
     * @Date: 2020/2/4 15:30
     * @reture: void
     **/
    void delete(AuthRoleRel condition);

    /**
     * @Author hmr
     * @Description 根据权限id获得相关角色的名称
     * @Date: 2020/5/25 15:48
     * @param id
     * @reture: java.util.List<java.lang.String>
     **/
    List<String> selectRoleNameByAuthId(Long id);
}