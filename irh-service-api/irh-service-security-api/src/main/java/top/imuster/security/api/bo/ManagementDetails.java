package top.imuster.security.api.bo;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.RoleInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ManagementDetails
 * @Description: security需要的对象
 * @author: hmr
 * @date: 2019/12/7 11:26
 */
public class ManagementDetails implements UserDetails {

    private ManagementInfo managementInfo;

    private List<RoleInfo> roleInfoList;

    private String token;

    public ManagementDetails(ManagementInfo managementInfo, List<RoleInfo> roleInfoList) {
        this.managementInfo = managementInfo;
        this.roleInfoList = roleInfoList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleInfoList.stream()
                .filter(roleInfo -> roleInfo.getRoleName() != null)
                .map(roleInfo -> new SimpleGrantedAuthority(roleInfo.getRoleName()))
                .collect(Collectors.toList());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ManagementInfo getManagementInfo() {
        return managementInfo;
    }

    public void setManagementInfo(ManagementInfo managementInfo) {
        this.managementInfo = managementInfo;
    }

    @Override
    public String getPassword() {
        return managementInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return managementInfo.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return managementInfo.getState() == 20;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {


        return managementInfo.getState() >= 20;
    }

    public List<RoleInfo> getRoleInfoList() {
        return roleInfoList;
    }

    public void setRoleInfoList(List<RoleInfo> roleInfoList) {
        this.roleInfoList = roleInfoList;
    }
}
