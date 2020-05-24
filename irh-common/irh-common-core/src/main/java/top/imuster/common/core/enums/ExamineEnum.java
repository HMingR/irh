package top.imuster.common.core.enums;

/**
 * @ClassName: ExamineEnum
 * @Description: ExamineEnum
 * @author: hmr
 * @date: 2020/5/21 15:02
 */
public enum  ExamineEnum {
    GOODS("info.examine.GOODS"),
    ARTICLE("info.examine.ARTICLE"),
    DEMAND("info.examine.DEMAND"),
    PROPAGATE("info.examine.PROPAGATE");

    String routingKey;

    ExamineEnum(String routingKey){
        this.routingKey = routingKey;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
