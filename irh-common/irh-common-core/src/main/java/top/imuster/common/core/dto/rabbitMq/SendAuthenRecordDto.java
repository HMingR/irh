package top.imuster.common.core.dto.rabbitMq;

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

    private String inputNum;

    //1-认证中  2-认证成功  3-认证失败
    private Integer result;

    //认证类型  1-一卡通  2-身份证
    private Integer authenType;

    public SendAuthenRecordDto(){
        super.setType(MqTypeEnum.AUTHEN_RECORD);
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getAuthenType() {
        return authenType;
    }

    public void setAuthenType(Integer authenType) {
        this.authenType = authenType;
    }

    public String getInputNum() {
        return inputNum;
    }

    public void setInputNum(String inputNum) {
        this.inputNum = inputNum;
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
