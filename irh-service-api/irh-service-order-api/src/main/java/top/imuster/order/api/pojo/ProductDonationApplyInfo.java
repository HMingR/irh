package top.imuster.order.api.pojo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ProductDonationApplyInfo extends BaseDomain {

	private static final long serialVersionUID = 15969687939L;

	public ProductDonationApplyInfo() {
		//默认无参构造方法
	}
	// 爱心捐款申请表
	@NotNull(groups = ValidateGroup.editGroup.class, message = "id不能未空")
	private Long id;

	// 申请人
	private Long applyUserId;

	//标题
	private String title;

	// 收款支付宝账号, max length: 13
	private String alipayNum;

	// 申请金额
	private String applyAmount;

	// 实际发放金额
	private String paymentAmount;

	// 申请理由, max length: 255
	private String reason;

	// 证明人姓名
	private String witnessName;

	// 证明人电话号码, max length: 11
	private String witnessPhone;

	// 申请凭证图片url
	private String reasonPicUrl;

	// 反馈, max length: 255
	private String feedback;

	// 反馈图片url
	private String feedbackPicUrl;

	// 1-删除  2-审核中  3-失败  4-审核通过 5-已转账
	//private Short state;

	//审核人
	private String approveUser;

	// 审核备注, max length: 255
	private String remark;

	private Long grantUser;

	//当前申请使用了哪些订单
	private List<OrderInfo> useOrders;

	private Integer userUpTotal;

	private Integer userDownTotal;

	public Integer getUserUpTotal() {
		return userUpTotal;
	}

	public void setUserUpTotal(Integer userUpTotal) {
		this.userUpTotal = userUpTotal;
	}

	public Integer getUserDownTotal() {
		return userDownTotal;
	}

	public void setUserDownTotal(Integer userDownTotal) {
		this.userDownTotal = userDownTotal;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<OrderInfo> getUserOrders() {
		return useOrders;
	}

	public void setUserOrders(List<OrderInfo> userOrders) {
		this.useOrders = userOrders;
	}

	public Long getGrantUser() {
		return grantUser;
	}

	public void setGrantUser(Long grantUser) {
		this.grantUser = grantUser;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getApplyUserId() {
		return this.applyUserId;
	}
    public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getAlipayNum() {
		return this.alipayNum;
	}
    public void setAlipayNum(String alipayNum) {
		this.alipayNum = alipayNum;
	}
	
	public String getApplyAmount() {
		return this.applyAmount;
	}
    public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public String getPaymentAmount() {
		return this.paymentAmount;
	}
    public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public String getReason() {
		return this.reason;
	}
    public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getWitnessName() {
		return this.witnessName;
	}
    public void setWitnessName(String witnessName) {
		this.witnessName = witnessName;
	}
	
	public String getWitnessPhone() {
		return this.witnessPhone;
	}
    public void setWitnessPhone(String witnessPhone) {
		this.witnessPhone = witnessPhone;
	}
	
	public String getReasonPicUrl() {
		return this.reasonPicUrl;
	}
    public void setReasonPicUrl(String reasonPicUrl) {
		this.reasonPicUrl = reasonPicUrl;
	}
	
	public String getFeedback() {
		return this.feedback;
	}
    public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public String getFeedbackPicUrl() {
		return this.feedbackPicUrl;
	}
    public void setFeedbackPicUrl(String feedbackPicUrl) {
		this.feedbackPicUrl = feedbackPicUrl;
	}
	
	public String getRemark() {
		return this.remark;
	}
    public void setRemark(String remark) {
		this.remark = remark;
	}
	
}