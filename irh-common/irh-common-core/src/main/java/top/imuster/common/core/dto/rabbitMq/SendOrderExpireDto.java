package top.imuster.common.core.dto.rabbitMq;

import top.imuster.common.core.enums.MqTypeEnum;

/**
 * @ClassName: SendOrderExpireDto
 * @Description: SendOrderExpireDto 订单支付超时时间
 * @author: hmr
 * @date: 2020/6/14 20:23
 */
public class SendOrderExpireDto extends Send2MQ {

    public SendOrderExpireDto(){
        super.setType(MqTypeEnum.ORDER_DLX);
    }

    private String ttl;

    private Long orderId;

    private Long userId;

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
