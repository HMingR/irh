package top.imuster.common.base.config.cache;

/**
 * @ClassName: RedisCachePrefix
 * @Description: 主要用来存放一些缓存前缀
 * @author: hmr
 * @date: 2020/6/4 10:55
 */
public interface RedisCachePrefix {

    //需求留言
    String DEMAND_REPLY_LIST = "demand::reply::list";

    String DEMAND_REPLY_TOTAL = "demand::reply::total";

    //用户需求
    String USER_DEMAND_LIST = "user::demand::list";

    //用户二手商品
    String USER_ES_PRODUCT_LIST = "user::es::product::list";
    String IRH_ERRAND_LIST = "user::errand::list";
}
