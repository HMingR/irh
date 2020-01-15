package top.imuster.common.core.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SendMessageDto
 * @Description: 发送邮箱信息的dto传输对象，向消息队列中传输这个类
 * @author: hmr
 * @date: 2020/1/12 14:53
 */
public class SendMessageDto implements Serializable {

    private static final long serialVersionUID = 1459082982569650090L;

    //主题
    private String topic;

    //发送的消息体
    private String body;

    //发送消息的来源类型  10-会员 20-管理员 30-系统
    private Integer sourceType;

    //发送消息的来源id
    private Long sourceId;

    //消息发送的时间
    private Date sendDate;

    //发送消息的类型  "EMAIL" 和 "SMS"
    private String type;

    //当消息具有一定的时效性时，需要保存在redis中，该值标识储存在redis中的key
    private String redisKey;

    //redis中储存的value
    private String value;

    //过期时间
    private Long expiration;

    //过期时间单位
    private TimeUnit unit;

    //目标id
    private Long targetId;

    @Override
    public String toString() {
        return "SendMessageDto{" +
                "topic='" + topic + '\'' +
                ", body='" + body + '\'' +
                ", sourceType=" + sourceType +
                ", sourceId=" + sourceId +
                ", sendDate=" + sendDate +
                ", type='" + type + '\'' +
                ", redisKey='" + redisKey + '\'' +
                ", value='" + value + '\'' +
                ", expiration=" + expiration +
                ", unit=" + unit +
                ", targetId=" + targetId +
                '}';
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public SendMessageDto() {
    }

    public SendMessageDto(String topic, String body, Integer sourceType, Long sourceId, Date sendDate, String type, Long expiration, TimeUnit unit) {
        this.topic = topic;
        this.body = body;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.sendDate = sendDate;
        this.type = type;
        this.expiration = expiration;
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }
}
