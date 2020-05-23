package top.imuster.user.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-05-21 19:27:46
 */
public class ExamineRecordInfo extends BaseDomain {

	private static final long serialVersionUID = 8353775316L;

	public ExamineRecordInfo() {
		//默认无参构造方法
	}
	//  商品、需求、文章审核表
	private Long id;

	//  目标id
	private Long targetId;

	//  1-二手商品  2-需求  3-文章
	private Integer type;

	//  1-通过  2-AI审核失败 3-人工审核失败
	//private Integer state;

	// AI审核备注, max length: 255
	private String remark;

	//  审核人
	private Long approveId;

	private Long releaseUserId;

	//审核人意见
	private String approveRemark;

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public Long getReleaseUserId() {
		return releaseUserId;
	}

	public void setReleaseUserId(Long releaseUserId) {
		this.releaseUserId = releaseUserId;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTargetId() {
		return this.targetId;
	}
    public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	
	public Long getApproveId() {
		return this.approveId;
	}
    public void setApproveId(Long approveId) {
		this.approveId = approveId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}