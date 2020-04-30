package top.imuster.security.api.dto;

/**
 * @ClassName: UserLoginDto
 * @Description: 用户登录
 * @author: hmr
 * @date: 2020/4/30 10:08
 */
public class UserLoginDto {

    //登录名   可能是邮箱 电话或者用户名
    private String loginName;

    //密码
    private String password;

    //验证码登录
    private String code;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
