package top.imuster.common.base.config;

/**
 * @ClassName: GlobalConstant
 * @Description: 用来记录一些在很多类中都需要共同使用的常量；不放在配置文件中，不然很难找
 * @author: hmr
 * @date: 2019/12/16 11:28
 */
public class GlobalConstant {

    public static final String IRH_CONSUMER_CODE_LOGIN = "irh::consumer::code::login::";
    public static final String IRH_TAG_NAMES_ZSET_KEY = "irh::tag::names::zset::key::";
    public static final String IRH_PRODUCT_COLLECT_MAP_KEY = "irh::product::collect::map::key";
    public static final String IRH_DEMAND_COLLECT_MAP_KEY = "irh::demand::collect::map::key";
    public static final String IRH_PRODUCT_ORDER_KEY = "irh::product::order::";
    public static final String IRH_ORDER_CODE_EXPIRE_KEY = "irh::order::code::expire::key::";
    public static final String IRH_PRODUCT_CONTENT_RECOMMEND_LIST_KEY = "irh::product::content::recommend::list::key::";
    public static final String IRH_PRODUCT_REAL_TIME_RECOMMEND_LIST_KEY = "irh::product::realtime::recommend::list::key::";
    public static final String IRH_WEB_CODE_KEY = "irh::web::code::key::";   //web页面登录请求的验证码
    public static final String IRH_USER_RESET_PWD_KEY = "irh::user::reset::pwd::"; //用户重置密码保存的验证码
    public static final String IRH_USER_BIND_WX_EMAIL_CODE = "irh::bind::wx::email::code::";
    public static final String IRH_PRODUCT_BROWSE_RECORD_BIT_MAP_KEY = "irh::product::browse::record::bitmap::key::";
    public static final String IRH_DEMAND_BROWSE_RECORD_BIT_MAP_KEY = "irh::demand::browse::record::bitmap::key::";
    public static final String IRH_ARTICLE_BROWSE_RECORD_BIT_MAP_KEY = "irh::article::browse::record::bitmap::key::";
    public static final String IRH_DEMAND_RECOMMEND_LIST_KEY = "irh::demand::recommend::list::key::";
    public static final String IRH_USER_OFFLINE_RECOMMEND_SET_KEY = "irh::user::offline::recommend::set::key::";


    public static final String ROLE_AUTH_MAP_KEY = "irh::role::auth::map";

    public static final String AUTH_HTTP_METHOD_MAP_KEY = "irh::http::method::map";
    //系统初始化时需要查出所有的权限和角色保存在redis里面
    public static final String IRH_ROLE_AND_AUTH_LIST = "irh::role::auth::list::key";
    public static final String IRH_FIVE_MINUTES_CACHE_KEY = "irh::five::minutes::cache::key";
    public static final String IRH_TEN_MINUTES_CACHE_KEY = "irh::ten::minutes::cache::key";
    public static final String IRH_THIRTY_MINUTES_CACHE_KEY = "irh::thirty::minutes::cache::key";
    public static final String IRH_ORDER_EXPIRE_KEY = "irh::order::expire::";

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

    //forum模块中保存在redis中的文章简略信息的key
    public static final String IRH_FORUM_ARTICLE = "article::info";

    //life模块中跑腿服务接单成功之后将跑腿信息表的id存放在redis中
    public static final String IRH_LIFE_ERRAND_MAP = "irh::life:errand::map";
    public static final String IRH_LIFE_ERRAND_KEY = "irh::life::errand::key::";

    //热搜
    public static final String IRH_FORUM_HOT_TOPIC_ZSET_KEY = "irh::forum::hot::topic";
    public static final String IRH_DEMAND_HOT_TOPIC_ZSET_KEY = "irh::demand::hot::topic";
    public static final String IRH_ES_HOT_TOPIC_ZSET_KEY = "irh::es::hot::topic";


    //redis中的一般缓存
    //一般内容的缓存，使用该字符串作为Cacheable的value时，其key值需要自行添加一个唯一标识符,不能只使用方法形参
    public static final String IRH_COMMON_CACHE_KEY = "irh::common::cache::key::";
    public static final String IRH_HOT_TOPIC_CACHE_KEY = "irh::hot::topic::cache::key::";   //forum模块热搜的缓存
    public static final String IRH_ARTICLE_USER_RANK_CACHE_KEY = "irh::hot::topic::cache::key::";   //forum用户排名缓存key

    public static final String IRH_USER_INFO_CACHE_KEY = "irh::user::info::cache::key::";   //用户信息缓存


    //redis中保存浏览次数的hash表的key
    public static final String IRH_FORUM_BROWSER_TIMES_MAP = "irh::forum::browser::times::map";

    //redis中记录文章转发次数
    public static final String IRH_FORUM_FORWARD_TIMES_MAP = "irh::forum::forward::times::map";


    //redis中记录用户浏览历史
    public static final String IRH_BROWSE_RECORD_ZSET = "irh::browse::record::zset::";


    //根据慈善基金申请id把自动生成的订单id保存到redis里面
    public static final String IRH_ORDER_DONATION = "irh::order::donation::";

    public static final String IRH_DONATION_APPLY_ATTRIBUTE_DOWN = "irh::donation::apply::attribute::down";
    public static final String IRH_DONATION_APPLY_ATTRIBUTE_UP = "irh::donation::apply::attribute::up";

}
