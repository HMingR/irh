package top.imuster.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap response = new LinkedHashMap();
        String name = authentication.getName();
        response.put("user_name", name);

        Object principal = authentication.getPrincipal();
        top.imuster.security.api.bo.UserDetails userDetails = null;
        if(principal instanceof top.imuster.security.api.bo.UserDetails){
            userDetails = (top.imuster.security.api.bo.UserDetails) principal;
        }else{
            //refresh_token默认不去调用userdetailService获取用户信息，这里我们手动去调用，得到 top.imuster.security.api.bo.UserDetails
            UserDetails userDetails1 = userDetailsService.loadUserByUsername(name);
            userDetails = (top.imuster.security.api.bo.UserDetails) userDetails1;
        }
        response.put("email", userDetails.getUsername());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }


}
