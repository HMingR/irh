package top.imuster.common.core.dto;

import java.io.Serializable;

/**
 * @ClassName: SendEmailDto
 * @Description: 发送邮箱信息的dto传输对象，向消息队列中传输这个类
 * @author: hmr
 * @date: 2020/1/12 14:53
 */
public class SendEmailDto implements Serializable {

    private static final long serialVersionUID = 1459082982569650090L;

    //发送的消息
    private String msg;

    //发送消息的来源类型  10-会员 20-管理员 30-系统
    private Integer sourceType;

    //发送消息的来源id
    private Long sourceId;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
