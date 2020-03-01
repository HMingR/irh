package top.imuster.common.core.dto;

import top.imuster.common.core.enums.MqTypeEnum;

/**
 * @ClassName: Send2MQ
 * @Description: Send2MQ
 * @author: hmr
 * @date: 2020/3/1 15:37
 */
public class Send2MQ {
    private MqTypeEnum type;

    public MqTypeEnum getType() {
        return type;
    }

    public void setType(MqTypeEnum type) {
        this.type = type;
    }
}
