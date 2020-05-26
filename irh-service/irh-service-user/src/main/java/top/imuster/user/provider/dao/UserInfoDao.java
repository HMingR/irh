package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.core.dto.UserDto;
import top.imuster.user.api.pojo.UserInfo;

import java.util.Map;

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
     * @Description
     * @Date: 2020/1/29 14:24
     * @param condition
     * @reture: top.imuster.user.api.pojo.UserInfo
     **/
    UserInfo selectUserInfoByEmail(String email);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户昵称
     * @Date: 2020/2/6 16:56
     * @param id
     * @reture: java.lang.String
     **/
    String selectUserNameById(Long id);

    /**
     * @Author hmr
     * @Description 根据时间获得该时间之前注册的人
     * @Date: 2020/2/26 11:29
     * @param s
     * @reture: long
     **/
    long selectUserTotalByCreateTime(String s);

    /**
     * @Author hmr
     * @Description 获得一个时间范围内用户注册量
     * @Date: 2020/2/26 11:37
     * @param start
     * @param end
     * @reture: long
     **/
    Long selectIncrementUserByTime(Map<String, String> param);

    /**
     * @Author hmr
     * @Description 通过id获得用户的简略信息
     * @Date: 2020/4/10 16:32
     * @param userId
     * @reture: top.imuster.common.core.dto.UserDto
     **/
    UserDto selectUserDtoById(Long userId);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/9 14:56
     * @param userId
     * @reture: java.util.Map<java.lang.String,java.lang.String> 返回的key为:address , phoneNum
     **/
    Map<String, String> selectAddAndPhoneById(Long userId);

    /**
     * @Author hmr
     * @Description 重置密码
     * @Date: 2020/5/16 11:16
     * @param userInfo
     * @reture: java.lang.Integer
     **/
    Integer updatePwdByEmail(UserInfo userInfo);
}