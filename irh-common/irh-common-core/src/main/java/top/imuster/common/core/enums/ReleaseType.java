package top.imuster.common.core.enums;

/**
 * @ClassName: ReleaseType
 * @Description: 发布的类型
 * @author: hmr
 * @date: 2020/4/24 10:12
 */
public enum ReleaseType {
    GOODS("info.1.release.1"),
    ARTICLE("info.2.release.2"),
    DEMAND("info.3.release.3");

    String routingKey;

    ReleaseType(String routingKey){
        this.routingKey = routingKey;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
