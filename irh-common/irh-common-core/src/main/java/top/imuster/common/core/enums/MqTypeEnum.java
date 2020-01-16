package top.imuster.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @ClassName: MqTypeEnum
 * @Description: 消息队列的类型
 * @author: hmr
 * @date: 2020/1/16 11:04
 */
public enum  MqTypeEnum {

    EMAIL("email", "info.1.email.1"),
    DETAIL("detail", "info.2.detail.2");

    String type;
    String routingKey;

    MqTypeEnum(String type, String routingKey) {
        this.type = type;
        this.routingKey = routingKey;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static MqTypeEnum getEnumByType(String type){
        MqTypeEnum[] values = MqTypeEnum.values();
        for (MqTypeEnum value : values) {
            if(value.type.equalsIgnoreCase(type)){
                return value;
            }
        }
        return null;
    }
}
