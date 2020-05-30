package top.imuster.auth.component.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import top.imuster.auth.service.Impl.UsernameUserDetailsServiceImpl;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Autowired
    UsernameUserDetailsServiceImpl usernameDetailsService;

    /**
     * @Author hmr
     * @Description 在jwt中保存更多的信息
     * @Date: 2020/5/16 20:03
     * @param authentication
     * @reture: java.util.Map<java.lang.String,?>
     **/
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap response = new LinkedHashMap();
        String name = authentication.getName();

        Object principal = authentication.getPrincipal();
        top.imuster.security.api.bo.UserDetails userDetails = null;
        if(principal instanceof top.imuster.security.api.bo.UserDetails){
            userDetails = (top.imuster.security.api.bo.UserDetails) principal;
        }else{
            //refresh_token默认不去调用userdetailService获取用户信息，这里我们手动去调用，得到 top.imuster.security.api.bo.UserDetails
            UserDetails userDetails1 = usernameDetailsService.loadUserByUsername(name);
            userDetails = (top.imuster.security.api.bo.UserDetails) userDetails1;
        }
        response.put("userId", userDetails.getUserInfo().getUserId());
        response.put("userType", userDetails.getUserInfo().getUserType());
        response.put("userState", userDetails.getUserInfo().getState());
        if (authentication.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(userDetails.getAuthorities()));
        }
        return response;
    }

}
