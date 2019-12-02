package top.imuster.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.imuster.user.pojo.ManagementInfo;
import top.imuster.user.pojo.RoleInfo;
import top.imuster.user.service.AuthRoleRelService;
import top.imuster.user.service.ManagementInfoService;
import top.imuster.user.service.ManagementRoleRelService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: ManagementDetailService
 * @Description: 管理人员登录认证
 * @author: hmr
 * @date: 2019/12/1 19:17
 */
public class ManagementDetailService implements UserDetailsService {

    @Resource
    ManagementInfoService managementInfoService;


    @Resource
    ManagementRoleRelService managementRoleRelService;

    @Resource
    AuthRoleRelService authRoleRelService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try{
            ManagementInfo managementer = managementInfoService.managementLoginByName(username);
            if(null == managementer){
                throw new UsernameNotFoundException("用户名或密码错误");
            }
            if(managementer.getState() <= 20){

            }
        }catch (Exception e){

        }
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

    /**
     * @Description: 获得管理员所有的角色
     * @Author: hmr
     * @Date: 2019/12/2 15:36
     * @param condition
     * @reture: java.util.List<java.lang.String>
     **/
    private final List<RoleInfo> getRole(ManagementInfo condition) throws Exception {
        return managementInfoService.getManagementRoleByCondition(condition).getRoleList();
    }

}
