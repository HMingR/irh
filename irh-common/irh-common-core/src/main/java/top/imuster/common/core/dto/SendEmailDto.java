package top.imuster.common.core.dto;

import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.enums.TemplateEnum;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SendEmailDto
 * @Description: 封装需要发送邮件的信息
 * @author: hmr
 * @date: 2020/3/1 15:11
 */
public class SendEmailDto extends Send2MQ implements Serializable {
    private static final long serialVersionUID = -4057679707386924027L;

    //接收邮件的地址
    private String email;

    //发送日期
    private String date;

    //如果发送的内容具有时效性,则标识存放在redis中的key
    private String redisKey;

    //内容或redisKey对应的value
    private String content;

    //过期时间
    private Long expiration;

    //过期时间单位
    private TimeUnit unit;

    public SendEmailDto(){
        this.setType(MqTypeEnum.EMAIL);
    }

    @Override
    public String toString() {
        return "SendEmailDto{" +
                "email='" + email + '\'' +
                ", date='" + date + '\'' +
                ", redisKey='" + redisKey + '\'' +
                ", content='" + content + '\'' +
                ", expiration=" + expiration +
                ", unit=" + unit +
                ", templateEnum=" + templateEnum +
                '}';
    }

    //邮件的模板
    private TemplateEnum templateEnum;

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public TemplateEnum getTemplateEnum() {
        return templateEnum;
    }

    public void setTemplateEnum(TemplateEnum templateEnum) {
        this.templateEnum = templateEnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
