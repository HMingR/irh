package top.imuster.common.core.enums;

/**
 * @ClassName: DetailType
 * @Description: 详情页的队列
 * @author: hmr
 * @date: 2020/5/16 10:24
 */
public enum DetailType {
    PROPAGATE("info.1.detail.1"),
    GOODS("info.2.detail.2");

    String routingKey;

    DetailType(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
