package top.imuster.common.base.config;

/**
 * @ClassName: GlobalConstant
 * @Description: 用来记录一些在很多类中都需要共同使用的常量；不放在配置文件中，不然很难找
 * @author: hmr
 * @date: 2019/12/16 11:28
 */
public class GlobalConstant {

    private GlobalConstant(){}

    public static final String USER_TOKEN_DTO = "userTokenDto";         //在本地线程map中的key

    //redis相关
    public static final Long REDIS_JWT_EXPIRATION = 1800L;              //redis中jwt的过期时间
    public static final String REDIS_ACCESS_TOKEN = "irh:token:accessToken:";  //redis中保存的token前缀

    //jwt相关
    public static final String JWT_TOKEN_HEADER = "Authorization";      //请求头中设置的字段，存放jwt
    public static final String JWT_SECRET = "irh-admin-secret";        //jwt加密解密的密钥
    public static final Long JWT_EXPIRATION = 1800L;               //jwt的超时(失效)时间，单位为秒
    public static final String JWT_TOKEN_HEAD = "Bearer ";               //Authorization头字段中的首部

    public static final String COOKIE_ACCESS_TOKEN_NAME = "userAccessToken";

    //redis中会员发布的需求浏览次数的key

    //redis中会员点赞记录的mapkey
    public static final String IRH_USER_UP_MAP = "IRH_USER_UP_MAP";

    //redis中留言或帖子点赞总数
    public static final String IRH_USER_UP_TOTAL = "IRH_USER_UP_TOTAL";

    public static final String IRH_ARTICLE_COLLECT_MAP = "IRH_ARTICLE_COLLECT_MAP";

    //使用邮箱更换密码时保存在redis中的key头部
    public static final String REDIS_RESET_PWD_EMAIL_TOKEN = "irh:reset:pwd:email:token:";

    //使用手机更换密码时保存在redis中的key头部
    public static final String REDIS_RESET_PWD_PHONE_TOKEN = "irh:reset:pwd:phone:token:";

    //会员注册时发送的邮箱验证码
    public static final String REDIS_CUSTOMER_EMAIL_REGISTER = "irh:customer:email:register:";

    //日志相关
    public static String getErrorLog(String operation){
        return operation + "失败,错误信息为{},实体信息为{}";
    }

}
