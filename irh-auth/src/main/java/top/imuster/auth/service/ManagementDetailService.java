package top.imuster.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.imuster.user.controller.UserControllerApi;

import javax.annotation.Resource;

/**
 * @ClassName: ManagementDetailService
 * @Description: 管理人员登录认证
 * @author: hmr
 * @date: 2019/12/1 19:17
 */
public class ManagementDetailService implements UserDetailsService {

    @Resource
    UserControllerApi userControllerClient;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        return null;
    }

    /**
     * @Description: 获得管理员所有的权限
     * @Author: hmr
     * @Date: 2019/12/2 15:36
     * @param roles
     * @reture: java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     **/
    /*private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<RoleInfo> roles) {
        return null;
        //return getGrantedAuthorities(getPrivileges(roles));
    }*/



}
