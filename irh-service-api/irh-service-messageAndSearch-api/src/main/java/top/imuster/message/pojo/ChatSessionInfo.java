package top.imuster.message.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-07-15 10:00:04
 */
public class ChatSessionInfo extends BaseDomain {

	private static final long serialVersionUID = 8306774595L;

	public ChatSessionInfo() {
		//默认无参构造方法
	}
	// 会话信息表
	private Long id;

	// 唯一的会话code, max length: 255
	private String sessionCode;

	//会话发起方id
	private Long sponsorId;

	//会话接收方id
	private Long receiverId;

	// 1-无效  2-有效, max length: 255
	//private String state;

	// 删除状态  1-leftUser删除   2-rightUser删除
	private Short deleteState;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getSessionCode() {
		return this.sessionCode;
	}
    public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Short getDeleteState() {
		return this.deleteState;
	}
    public void setDeleteState(Short deleteState) {
		this.deleteState = deleteState;
	}
	 
}