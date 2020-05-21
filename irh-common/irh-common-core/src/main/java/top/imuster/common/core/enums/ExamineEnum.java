package top.imuster.common.core.enums;

/**
 * @ClassName: ExamineEnum
 * @Description: ExamineEnum
 * @author: hmr
 * @date: 2020/5/21 15:02
 */
public enum  ExamineEnum {
    GOODS("info.1.examine.1"),
    ARTICLE("info.2.examine.2"),
    DEMAND("info.3.examine.3"),
    PROPAGATE("info.4.examine.4");

    String routingKey;

    ExamineEnum(String routingKey){
        this.routingKey = routingKey;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
