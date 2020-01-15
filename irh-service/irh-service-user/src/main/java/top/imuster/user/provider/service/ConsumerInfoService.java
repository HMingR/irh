package top.imuster.user.provider.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.xml.internal.bind.v2.TODO;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.user.api.bo.ConsumerDetails;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.pojo.ConsumerInfo;

import java.lang.reflect.InvocationTargetException;

/**
 * ConsumerInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ConsumerInfoService extends BaseService<ConsumerInfo, Long> {
    /**
     * @Description: 会员登录使用
     * @Author: hmr
     * @Date: 2019/12/26 15:54
     * @param email
     * @param password
     * @reture: java.lang.String 返回token
     **/
    String login(String email, String password);

    /**
     * @Description:
     * @Author: hmr
     * @Date: 2019/12/26 15:54
     * @param userName
     * @reture: top.imuster.user.api.bo.ConsumerDetails
     **/
    ConsumerDetails loadConsumerDetailsByName(String userName);

    /**
     * @Description: 根据用户信息校验用户是否存在
     * @Author: hmr
     * @Date: 2020/1/13 10:28
     * @param consumerInfo
     * @reture: boolean 如果存在，返回false；如果不存在，则返回true
     **/
    boolean checkValid(CheckValidDto checkValidDto) throws Exception;

    /**
     * @Author hmr
     * @Description 用户注册的一系列逻辑
     * @Date: 2020/1/14 13:00
     * @param consumerInfo
     * @reture: void
     **/
    void register(ConsumerInfo consumerInfo, String code) throws Exception;

    /**
     * @Author hmr
     * @Description 发送验证码
     * @Date: 2020/1/15 10:41
     * @param sendMessageDto
     * @param email
     * @param type 1-注册验证码   2-重置密码验证码
     * @reture: void
     **/
    void getCode(SendMessageDto sendMessageDto, String email, Integer type) throws JsonProcessingException;

}