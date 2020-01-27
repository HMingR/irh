package top.imuster.security.api.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.api.pojo.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: UserDetails
 * @Description: UserDetails
 * @author: hmr
 * @date: 2020/1/27 21:42
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    UserInfo userInfo;

    List<RoleInfo> roleInfos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleInfos.stream()
                .filter(roleInfo -> roleInfo.getRoleName() != null)
                .map(roleInfo -> new SimpleGrantedAuthority(roleInfo.getRoleName()))
                .collect(Collectors.toList());
    }

    public UserDetails(UserInfo userInfo){
        this.roleInfos = userInfo.getRoleList();
        this.userInfo = userInfo;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userInfo.getState() == 20;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userInfo.getState() >= 20;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<RoleInfo> getRoleInfos() {
        return roleInfos;
    }

    public void setRoleInfos(List<RoleInfo> roleInfos) {
        this.roleInfos = roleInfos;
    }
}
