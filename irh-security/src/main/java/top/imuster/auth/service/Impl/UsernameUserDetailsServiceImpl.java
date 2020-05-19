package top.imuster.auth.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import top.imuster.common.core.dto.UserDto;
import top.imuster.security.api.bo.UserDetails;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.util.List;

//@Service("usernameDetailsService")
public class UsernameUserDetailsServiceImpl implements UserDetailsService {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    ClientDetailsService clientDetailsService;

    /**
     * @Author hmr
     * @Description 默认是使用邮箱作为用户名
     * @Date: 2020/1/28 20:48
     * @param username
     * @reture: org.springframework.security.core.userdetails.UserDetails
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        /*f(authentication == null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }*/

        UserInfo userInfo = userServiceFeignApi.loadUserInfoByEmail(username);
        if(userInfo == null) throw new AuthenticationServiceException("用户名不存在,请检查用户名是否输入正确");
        if(userInfo.getState() == null || userInfo.getState() <= 20){
            throw new AuthenticationServiceException("该账号已被冻结,请联系管理员");
        }

        String userAuth = "";
        if(userInfo.getType() != 10){
            List<String> roleName = userServiceFeignApi.getRoleByUserName(username);
            userAuth  = StringUtils.join(roleName.toArray(), ",");
        }
        UserDto userDto = new UserDto(userInfo.getId(), userInfo.getEmail(), userInfo.getNickname(), userInfo.getPortrait(), userInfo.getType());
        UserDetails userDetails = new UserDetails(userInfo.getEmail(), userInfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(userAuth));
        userDetails.setUserInfo(userDto);
        return userDetails;
    }
}
