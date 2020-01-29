package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.UserInfo;

/**
 * UserInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface UserInfoDao extends BaseDao<UserInfo, Long> {
    //自定义扩展

    /**
     * @Description: 校验用户注册时的某些信息是否唯一
     * @Author: hmr
     * @Date: 2020/1/13 10:31
     * @param userInfo
     * @reture: int
     **/
    int checkInfo(UserInfo userInfo);

    String selectEmailById(Long id);

    /**
     * @Author hmr
     * @Description 根据条件查询用户的角色和角色对应的权限
     * @Date: 2020/1/29 14:24
     * @param condition
     * @reture: top.imuster.user.api.pojo.UserInfo
     **/
    UserInfo selectUserRoleByCondition(UserInfo condition);

}