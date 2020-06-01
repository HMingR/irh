package top.imuster.common.core.enums;

/**
 * @ClassName: BrowserType
 * @Description: 和BrowserTimes注解配合使用
 * @author: hmr
 * @date: 2020/1/23 17:26
 */
public enum BrowserType {
    ES_SELL_PRODUCT(1, "es::product::browser::times::map"),
    ES_DEMAND_PRODUCT(2, "demand::product::browser::times::map"),
    FORUM(3, "forum::article::browser::times::map"),
    PROPAGATE(4, "propagate::browser::times::map"),    //广告或通知浏览次数
    PRODUCT_ROTATION(5, "product::rotation::browser::times::map");   //商城首页轮播图点击次数
    public Integer type;
    private String redisKeyHeader;

    BrowserType(Integer type, String redisKeyHeader) {
        this.type = type;
        this.redisKeyHeader = redisKeyHeader;
    }

    public Integer getType() {
        return type;
    }

    public String getRedisKeyHeader() {
        return redisKeyHeader;
    }
}
