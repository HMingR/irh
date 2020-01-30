package top.imuster.user.api.dto;

import java.util.List;

/**
 * @ClassName: UserJwt
 * @Description: UserJwt
 * @author: hmr
 * @date: 2020/1/29 21:17
 */
public class UserJwt {
    private String user_name;

    private List<String> scope;

    private Long exp;

    private String email;

    private List<String> authorities;

    private String jti;

    private String client_id;

    @Override
    public String toString() {
        return "UserJwt{" +
                "user_name='" + user_name + '\'' +
                ", scope=" + scope +
                ", exp=" + exp +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                ", jti='" + jti + '\'' +
                ", client_id='" + client_id + '\'' +
                '}';
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
