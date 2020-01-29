package top.imuster.security.api.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
public class UserDetails extends User {

    UserInfo userInfo;

    List<RoleInfo> roleInfos;

    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
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
