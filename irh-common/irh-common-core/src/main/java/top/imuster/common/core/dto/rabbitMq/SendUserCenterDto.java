package top.imuster.common.core.dto.rabbitMq;

import top.imuster.common.core.enums.MqTypeEnum;

import java.io.Serializable;

/**
 * @ClassName: SendUserCenterDto
 * @Description: 发送给用户中心的
 * @author: hmr
 * @date: 2020/3/1 16:30
 */
public class SendUserCenterDto extends Send2MQ  implements Serializable {

    private static final long serialVersionUID = -8327352278236574726L;
    //发送方
    private Long fromId;

    //接受方
    private Long toId;

    //发送日期
    private String date;

    //内容
    private String content;

    //目标id(比如留言，那么该值标识新的留言记录)
    private Long resourceId;

    //源id(比如留言，如果是一级留言的话那么标识商品的信息，如果是二级留言，则标识parentId的值；指的是原来在数据库中的；一般用于回复)
    private Long targetId;

    //消息类型  10-商城留言  20-论坛留言  30-跑腿订单通知  40-商城订单通知 50-商城公益基金 60-商城评价  70-其他系统通知 80-商品审核 85-需求审核 90-文章审核 100-跑腿评价通知
    private Integer newsType;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public SendUserCenterDto(){
        this.setType(MqTypeEnum.CENTER);
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
