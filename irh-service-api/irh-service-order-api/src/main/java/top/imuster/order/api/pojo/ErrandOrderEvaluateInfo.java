package top.imuster.order.api.pojo;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author 黄明人
 * @since 2020-06-19 19:54:16
 */
public class ErrandOrderEvaluateInfo extends BaseDomain {

	private static final long serialVersionUID = 9718248432L;

	public ErrandOrderEvaluateInfo() {
		//默认无参构造方法
	}
	// 跑腿评价表主键
	private Long id;

	// 跑腿订单表的主键id
	@NotNull(groups = ValidateGroup.addGroup.class, message = "跑腿订单的id不能为空")
	private Long errandOrderId;

	// 写评价的人
	private Long userId;

	// 评价 1-不满意  2-满意
	@NotNull(groups = ValidateGroup.addGroup.class, message = "评价等级不能为空")
	private Integer evaluate;

	// 评价内容, max length: 255
	private String evaluateContent;

	//接单者的id
	@NotNull(groups = ValidateGroup.addGroup.class, message = "接单者不能为空")
	private Long holderId;

	//跑腿订单编号
	@NotNull(groups = ValidateGroup.addGroup.class, message = "跑腿订单号不能为空")
	private String errandOrderCode;

//	private Date createTime;

//	private Date updateTime;

	// 1-无效  2-有效
//	private Short state;

	public String getErrandOrderCode() {
		return errandOrderCode;
	}

	public void setErrandOrderCode(String errandOrderCode) {
		this.errandOrderCode = errandOrderCode;
	}

	public Long getHolderId() {
		return holderId;
	}

	public void setHolderId(Long holderId) {
		this.holderId = holderId;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getErrandOrderId() {
		return this.errandOrderId;
	}
    public void setErrandOrderId(Long errandOrderId) {
		this.errandOrderId = errandOrderId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Integer evaluate) {
		this.evaluate = evaluate;
	}

	public String getEvaluateContent() {
		return this.evaluateContent;
	}
    public void setEvaluateContent(String evaluateContent) {
		this.evaluateContent = evaluateContent;
	}
}