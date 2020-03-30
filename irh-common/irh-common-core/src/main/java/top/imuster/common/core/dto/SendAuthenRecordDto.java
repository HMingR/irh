package top.imuster.common.core.dto;

import top.imuster.common.core.enums.MqTypeEnum;

import java.io.Serializable;

/**
 * @ClassName: SendAuthenRecordDto
 * @Description: SendAuthenRecordDto
 * @author: hmr
 * @date: 2020/3/29 15:39
 */
public class SendAuthenRecordDto extends Send2MQ implements Serializable {

    private static final long serialVersionUID = 3497053642601572141L;
    private Long userId;

    private String picUri;

    private String inputName;

    public SendAuthenRecordDto(){
        super.setType(MqTypeEnum.AUTHEN_RECORD);
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }
}
