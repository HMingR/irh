package top.imuster.common.core.dto.rabbitMq;

import top.imuster.common.core.enums.MqTypeEnum;

/**
 * @ClassName: SendOrderEvaluateDto
 * @Description: 订单自动完成并评价
 * @author: hmr
 * @date: 2020/6/16 10:15
 */
public class SendOrderEvaluateDto extends SendDead2MQ {

    public SendOrderEvaluateDto(){
        super.setType(MqTypeEnum.ORDER_EVALUATE);
    }

}
