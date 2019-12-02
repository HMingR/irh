package top.imuster.user.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ClassName: AnyUser
 * @Description: 所有用户通用的user对象，继承自org.springframework.security.core.userdetails.User
 * @author: hmr
 * @date: 2019/12/2 15:14
 */
public class AnyUser extends User implements Serializable {
    private static final long serialVersionUID = 1097510905970042500L;

    private Long id;
    private String nickName;

    public AnyUser(
            String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
