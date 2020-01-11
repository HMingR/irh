package top.imuster.user.api.pojo;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
public class ReportFeedbackInfo extends BaseDomain {

	private static final long serialVersionUID = 5219703142L;

	public ReportFeedbackInfo() {
		//默认无参构造方法
	}
	// 举报反馈表的主键
	@NotEmpty(groups = ValidateGroup.processGroup.class, message = "处理反馈的id不能为空")
	private Long id;

	// 1-商品举报 2-留言举报 3-评价举报 4-帖子举报
	private Integer type;

	//  举报对象的id
	private Long targetId;

	// 举报人id
	private Long customerId;

	//处理结果  1-举报失败   2-举报成功
	@NotEmpty(groups = ValidateGroup.processGroup.class, message = "处理结果不能为空")
	private Integer result;

	//反馈备注
	private String remark;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getType() {
		return this.type;
	}
    public void setType(Integer type) {
		this.type = type;
	}
	
	public Long getTargetId() {
		return this.targetId;
	}
    public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	
	public Long getCustomerId() {
		return this.customerId;
	}
    public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	 
}