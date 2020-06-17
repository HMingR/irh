package top.imuster.common.core.dto.rabbitMq;

/**
 * @ClassName: SendDead2MQ
 * @Description: 发送死信队列
 * @author: hmr
 * @date: 2020/6/16 10:23
 */
public class SendDead2MQ extends Send2MQ{

    private Long orderId;

    private Long userId;

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
