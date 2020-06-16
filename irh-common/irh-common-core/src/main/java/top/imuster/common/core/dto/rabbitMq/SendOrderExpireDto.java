package top.imuster.common.core.dto.rabbitMq;

import top.imuster.common.core.enums.MqTypeEnum;

/**
 * @ClassName: SendOrderExpireDto
 * @Description: SendOrderExpireDto 订单支付超时时间
 * @author: hmr
 * @date: 2020/6/14 20:23
 */
public class SendOrderExpireDto extends SendDead2MQ {

    public SendOrderExpireDto(){
        super.setType(MqTypeEnum.ORDER_DLX);
    }
}
