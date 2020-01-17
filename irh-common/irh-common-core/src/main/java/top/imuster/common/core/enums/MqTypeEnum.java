package top.imuster.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @ClassName: MqTypeEnum
 * @Description: 消息队列的类型
 * @author: hmr
 * @date: 2020/1/16 11:04
 */
public enum  MqTypeEnum {

    EMAIL("email", "info.#.email.#", "queue_inform_email"),
    DETAIL("detail", "info.#.detail.#", "queue_inform_detail"),
    CENTER("center", "info.#.center.#", "queue_inform_center");

    String type;
    String routingKey;
    String queueName;

    MqTypeEnum(String type, String routingKey, String queueName) {
        this.type = type;
        this.routingKey = routingKey;
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
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
