package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.pojo.UserInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * ConsumerInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface UserInfoService extends BaseService<UserInfo, Long> {

    /**
     * @Description:
     * @Author: hmr
     * @Date: 2019/12/26 15:54
     * @param userName
     * @reture: top.imuster.security.api.bo.ConsumerDetails
     **/
    UserInfo loadUserDetailsByEmail(String email);

    /**
     * @Description: 根据用户信息校验用户是否存在
     * @Author: hmr
     * @Date: 2020/1/13 10:28
     * @param checkValidDto
     * @reture: boolean 如果存在，返回false；如果不存在，则返回true
     **/
    boolean checkValid(CheckValidDto checkValidDto) throws Exception;

    /**
     * @Author hmr
     * @Description 用户注册的一系列逻辑
     * @Date: 2020/1/14 13:00
     * @param userInfo
     * @reture: void
     **/
    Message<String> register(UserInfo userInfo, String code) throws InvocationTargetException, IllegalAccessException;

    /**
     * @Author hmr
     * @Description 根据id获得邮箱
     * @Date: 2020/1/17 10:21
     * @param id
     * @reture: java.lang.String
     **/
    String getEmailById(Long id);

    /**
     * @Author hmr
     * @Description 根据用户id修改用户的角色
     * @Date: 2020/1/31 14:49
     * @param userId 用户id
     * @param roleIds 角色id集合，用逗号分隔
     * @reture: void
     **/
    void editUserRoleById(Long userId, String roleIds);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户昵称
     * @Date: 2020/2/6 16:55
     * @param id
     * @reture: java.lang.String
     **/
    String getUserNameById(Long id);

    /**
     * @Author hmr
     * @Description 根据创建时间获得在该时间之前注册的用户数量
     * @Date: 2020/2/26 11:26
     * @param s
     * @reture: long
     **/
    long getUserTotalByCreateTime(String s);

    /**
     * @Author hmr
     * @Description 获得一个时间区域内的用户注册数量
     * @Date: 2020/2/26 11:36
     * @param start
     * @param end
     * @reture: long
     **/
    Long getIncrementUserByTime(String start, String end);

    /**
     * @Author hmr
     * @Description 根据id获得用户的简略信息
     * @Date: 2020/4/10 16:31
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.core.dto.UserDto>
     **/
    Message<UserDto> getUserDtoByUserId(Long userId);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/9 14:55
     * @param userId
     * @reture: java.util.Map<java.lang.String,java.lang.String>  返回的key为:address , phoneNum
     **/
    Map<String, String> getAddAndPhoneById(Long userId);

    /**
     * @Author hmr
     * @Description 根据email更新pwd
     * @Date: 2020/5/16 11:14
     * @param email
     * @param password
     * @reture: boolean
     **/
    Message<String> resetPwdByEmail(UserInfo userInfo);
}