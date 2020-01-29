package top.imuster.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import top.imuster.security.api.bo.UserDetails;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

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
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        UserInfo userInfo = userServiceFeignApi.loadUserInfoByEmail(username);

        if(userInfo == null){
            return null;
        }
        //todo
        log.info("查询到的用户信息为{}", userInfo);
        /*List<SimpleGrantedAuthority> collect = roleList.stream()
                .filter(roleInfo -> roleInfo.getRoleName() != null)
                .map(roleInfo -> new SimpleGrantedAuthority(roleInfo.getRoleName()))
                .collect(Collectors.toList());*/

        List<String> collect = new ArrayList<>();
        collect.add("login");
        String userAuth  = StringUtils.join(collect.toArray(), ",");
        UserDetails userDetails = new UserDetails(userInfo.getEmail(), userInfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(userAuth));
        userDetails.setUserInfo(userInfo);
        return userDetails;
    }
}
