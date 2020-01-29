package top.imuster.security.api.bo;

import java.io.Serializable;

/**
 * @ClassName: AuthToken
 * @Description: AuthToken
 * @author: hmr
 * @date: 2020/1/29 14:59
 */
public class AuthToken implements Serializable {

    private static final long serialVersionUID = 1226549825065835858L;

    String accessToken;//访问token就是短令牌，用户身份令牌

    String refreshToken;//刷新token

    String jwtToken;//jwt令牌

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
