package top.imuster.message.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
@ApiModel("消息实体类")
public class NewsInfo extends BaseDomain {

	private static final long serialVersionUID = 5593091503L;

	public NewsInfo() {
		//默认无参构造方法
	}
	// 消息表主键
	@ApiModelProperty("消息表主键")
	private Long id;

	// 接收方id
	@ApiModelProperty("接收方id")
	private Long receiverId;

	//发送方id  当发送方id为0时标识系统推送
	@ApiModelProperty("发送方id")
	private Long senderId;

	// 标题, max length: 255
	@ApiModelProperty("标题")
	private String title;

	// 内容
	@ApiModelProperty("内容")
	private String content;

	// 消息类型 10:系统通知
	private Short newsType;

	// 10:删除  20:已读  30:未读
	//private Short state;


	@Override
	public String toString() {
		return "NewsInfo{" +
				"id=" + id +
				", receiverId=" + receiverId +
				", senderId=" + senderId +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				'}';
	}

	public Short getNewsType() {
		return newsType;
	}

	public void setNewsType(Short newsType) {
		this.newsType = newsType;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getReceiverId() {
		return this.receiverId;
	}
    public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	
	public String getTitle() {
		return this.title;
	}
    public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
}