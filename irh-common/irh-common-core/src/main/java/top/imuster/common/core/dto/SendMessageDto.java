package top.imuster.common.core.dto;

import top.imuster.common.core.enums.MqTypeEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SendMessageDto
 * @Description: 发送邮箱信息的dto传输对象，向消息队列中传输这个类
 * 只发送mq必须有: topic  body  sourceType  sourceId  type
 * @author: hmr
 * @date: 2020/1/12 14:53
 */
public class SendMessageDto implements Serializable {

    private static final long serialVersionUID = 1459082982569650090L;

    //消息中心的主键
    private Long id;

    //主题
    private String topic;

    //发送的消息体
    private String body;

    //发送消息的来源类型  10-会员 20-系统
    private Integer sourceType;

    //发送消息的来源id
    private Long sourceId;

    //消息发送的时间
    private Date sendDate;

    //接收消息的id
    private String sendTo;

    //发送消息的类型
    private MqTypeEnum type;

    //当消息具有一定的时效性时，需要保存在redis中，该值标识储存在redis中的key
    private String redisKey;

    //redis中储存的value
    private String value;

    //过期时间
    private Long expiration;

    //过期时间单位
    private TimeUnit unit;

    //(被举报的信息)id
    private Long targetId;

    public SendMessageDto(String topic, String body, Integer sourceType, Long sourceId, Date sendDate, String sendTo, MqTypeEnum type, Long targetId) {
        this.topic = topic;
        this.body = body;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.sendDate = sendDate;
        this.sendTo = sendTo;
        this.type = type;
        this.targetId = targetId;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SendMessageDto() {
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public MqTypeEnum getType() {
        return type;
    }

    public void setType(MqTypeEnum type) {
        this.type = type;
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
