package top.imuster.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @ClassName: MqTypeEnum
 * @Description: 消息队列的类型
 * @author: hmr
 * @date: 2020/1/16 11:04
 */
public enum MqTypeEnum {

    EMAIL("email", "info.1.email.1", "queue_inform_email", "info.#.email.#"),
    DETAIL("detail", "info.2.detail.2", "queue_inform_detail","info.#.detail.#"),
    CENTER("center", "info.3.center.3", "queue_inform_center", "info.#.center.#");

    String type;
    String routingKey;
    String queueName;
    String routingKeyMatchRule;

    MqTypeEnum(String type, String routingKey, String queueName, String routingKeyMatchRule) {
        this.type = type;
        this.routingKey = routingKey;
        this.queueName = queueName;
        this.routingKeyMatchRule = routingKeyMatchRule;
    }

    public String getRoutingKeyMatchRule() {
        return routingKeyMatchRule;
    }

    public void setRoutingKeyMatchRule(String routingKeyMatchRule) {
        this.routingKeyMatchRule = routingKeyMatchRule;
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
