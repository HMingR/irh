package top.imuster.order.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDonationOrderRel extends BaseDomain {

	private static final long serialVersionUID = 5891719254L;

	public ProductDonationOrderRel() {
		//默认无参构造方法
	}
	// 爱心捐赠申请表和商品订单关系表
	private Long id;

	// 订单表的主键id
	private Long orderId;

	// 申请表id
	private Long donationApplyId;

	// 操作人
	private Long operationUserId;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOrderId() {
		return this.orderId;
	}
    public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getDonationApplyId() {
		return this.donationApplyId;
	}
    public void setDonationApplyId(Long donationApplyId) {
		this.donationApplyId = donationApplyId;
	}
	
	public Long getOperationUserId() {
		return this.operationUserId;
	}
    public void setOperationUserId(Long operationUserId) {
		this.operationUserId = operationUserId;
	}
}