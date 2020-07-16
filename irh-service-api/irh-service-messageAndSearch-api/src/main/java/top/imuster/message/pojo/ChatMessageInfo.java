package top.imuster.message.pojo;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
public class ChatMessageInfo extends BaseDomain {

	private static final long serialVersionUID = 9329423307L;

	public ChatMessageInfo() {
		//默认无参构造方法
	}
	// 聊天记录表主键
	private Long id;

	// 聊天内容, max length: 255
	@NotNull(groups = ValidateGroup.addGroup.class)
	private String content;

	// sessionCode
	@NotNull(groups = ValidateGroup.addGroup.class)
	private String sessionCode;

	// 发送消息的人
	private Long writerId;

	// 1-删除   2-撤回  3-有效
	//private Short state;

	// 1-已读 2-未读
	private Integer readState;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
	public String getSessionCode() {
		return this.sessionCode;
	}
    public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}
	
	public Long getWriterId() {
		return this.writerId;
	}
    public void setWriterId(Long writerId) {
		this.writerId = writerId;
	}

	public Integer getReadState() {
		return readState;
	}

	public void setReadState(Integer readState) {
		this.readState = readState;
	}
}