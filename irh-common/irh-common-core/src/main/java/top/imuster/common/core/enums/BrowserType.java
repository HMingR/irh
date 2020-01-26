package top.imuster.common.core.enums;

/**
 * @ClassName: BrowserType
 * @Description: 和BrowserTimes注解配合使用
 * @author: hmr
 * @date: 2020/1/23 17:26
 */
public enum BrowserType {
    ES_SELL_PRODUCT(1, "es:product:browser:times:"),
    ES_DEMAND_PRODUCT(2, "demand:product:browser:times:"),
    FORUM(3, "论坛帖子");
    public Integer type;
    private String redisKeyHeader;

    BrowserType(Integer type, String redisKeyHeader) {
        this.type = type;
        this.redisKeyHeader = redisKeyHeader;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRedisKeyHeader() {
        return redisKeyHeader;
    }

    public void setRedisKeyHeader(String redisKeyHeader) {
        this.redisKeyHeader = redisKeyHeader;
    }
}
