package top.imuster.common.core.dto;

import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;

/**
 * @ClassName: SendReleaseDto
 * @Description: 存放到消息队列中的对象
 * @author: hmr
 * @date: 2020/4/24 10:37
 */
public class SendReleaseDto extends Send2MQ {

    public SendReleaseDto(){
        this.setType(MqTypeEnum.RELEASE);
    }

    BaseDomain targetInfo;

    OperationType operationType;

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BaseDomain getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(BaseDomain targetInfo) {
        this.targetInfo = targetInfo;
    }

    public void setReleaseType(ReleaseType releaseType) {
        MqTypeEnum.RELEASE.setRelease(releaseType);
    }
}
