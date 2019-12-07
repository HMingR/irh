package top.imuster.user.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.imuster.user.pojo.AuthInfo;
import top.imuster.user.pojo.ManagementInfo;

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

    private List<AuthInfo> authInfoList;

    public ManagementDetails(ManagementInfo managementInfo, List<AuthInfo> authInfoList) {
        this.managementInfo = managementInfo;
        this.authInfoList = authInfoList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authInfoList.stream()
                .filter(authInfo -> authInfo.getAuthName() != null)
                .map(authInfo -> new SimpleGrantedAuthority(authInfo.getAuthName()))
                .collect(Collectors.toList());
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
}
