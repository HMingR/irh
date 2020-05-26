package top.imuster.auth.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.security.api.pojo.UserLoginInfo;
import top.imuster.security.api.pojo.WxAppLoginInfo;
import top.imuster.user.api.dto.BindWxDto;

/**
 * WxAppLoginService接口
 * @author 黄明人
 * @since 2020-05-19 15:28:28
 */
public interface WxAppLoginService extends BaseService<WxAppLoginInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据openId获得用户信息
     * @Date: 2020/5/19 15:34
     * @param openId
     * @reture: top.imuster.user.api.pojo.UserInfo
     **/
    UserLoginInfo loginByOpenId(String openId);

    /**
     * @Author hmr
     * @Description 绑定微信账号
     * @Date: 2020/5/19 18:18
     * @param bindDto
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> binding(BindWxDto bindDto);

    /**
     * @Author hmr
     * @Description 发送绑定微信的邮箱验证码
     * @Date: 2020/5/19 18:34
     * @param userDto
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> sendBindingEmail(UserDto userDto);
}