package top.imuster.auth.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.security.api.pojo.UserLoginInfo;

/**
 * UserLoginInfoService接口
 * @author 黄明人
 * @since 2020-05-26 09:40:11
 */
public interface UserLoginInfoService extends BaseService<UserLoginInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据登录名获得用户信息
     * @Date: 2020/5/26 10:03
     * @param loginName
     * @reture: top.imuster.security.api.pojo.UserLoginInfo
     **/
    UserLoginInfo getInfoByLoginName(String loginName);

}