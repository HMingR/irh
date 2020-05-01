package top.imuster.auth.config;

/**
 * @ClassName: SecurityConstants
 * @Description: security模块一些常用的参数
 * @author: hmr
 * @date: 2020/4/30 12:23
 */
public interface SecurityConstants {
    //在验证码登录的操作中,request请求中邮箱或者手机号的参数名
    String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    String EMAIL_VERIFY_CODE = "emailVerifyCode";

    String LOGIN_TYPE = "loginType";

    String LOGIN_PARAM_NAME = "loginName";

    //邮箱验证码的方式登录
    String EMAIL_CODE = "emailCode";

    //用户名密码登录
    String USER_PWD = "usePwd";

    //密码登录的时候密码的参数名
    String PASSWORD = "password";
}
