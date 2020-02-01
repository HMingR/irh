package top.imuster.security.api.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import top.imuster.common.core.dto.UserDto;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.api.pojo.UserInfo;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName: UserDetails
 * @Description: UserDetails
 * @author: hmr
 * @date: 2020/1/27 21:42
 */
public class UserDetails extends User {

    UserDto userInfo;

    List<RoleInfo> roleInfos;

    public UserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public List<RoleInfo> getRoleInfos() {
        return roleInfos;
    }

    public void setRoleInfos(List<RoleInfo> roleInfos) {
        this.roleInfos = roleInfos;
    }

    public UserDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserDto userInfo) {
        this.userInfo = userInfo;
    }
}
