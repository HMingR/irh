package top.imuster.common.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @ClassName: MqTypeEnum
 * @Description: 消息队列的类型
 * @author: hmr
 * @date: 2020/1/16 11:04
 */
public enum MqTypeEnum {

    EMAIL("email", "info.1.email.1", "queue_inform_email", "info.#.email.#", "发送邮箱"),
    DETAIL("detail", "info.2.detail.2", "queue_inform_detail","info.#.detail.#", "生成详情页"),
    CENTER("center", "info.3.center.3", "queue_inform_center", "info.#.center.#", "消息中心"),
    ERRAND("errand", "info.4.errand.4", "queue_info_errand", "info.#.errand.#", "life模块控制跑腿并发");

    String type;
    String routingKey;
    String queueName;
    String routingKeyMatchRule;
    String desc;

    MqTypeEnum(String type, String routingKey, String queueName, String routingKeyMatchRule, String desc) {
        this.type = type;
        this.routingKey = routingKey;
        this.queueName = queueName;
        this.routingKeyMatchRule = routingKeyMatchRule;
        this.desc = desc;
    }

    public String getRoutingKeyMatchRule() {
        return routingKeyMatchRule;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getRoutingKey() {
        return routingKey;
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
