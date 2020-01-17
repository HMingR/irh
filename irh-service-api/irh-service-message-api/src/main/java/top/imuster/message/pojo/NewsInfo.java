package top.imuster.message.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
public class NewsInfo extends BaseDomain {

	private static final long serialVersionUID = 5593091503L;

	public NewsInfo() {
		//默认无参构造方法
	}
	// 消息表主键
	private Long id;

	// 接收方id
	private Long receiverId;

	// 标题, max length: 255
	private String title;

	// 内容
	private String content;

	// 消息类型 10:系统通知
//	private Short newsType;

	// 10:删除  20:发送失败  30:已发送  40:未读  50:已读
	//private Short state;


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