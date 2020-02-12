package top.imuster.life.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
public class ErrandOrder extends BaseDomain {

	private static final long serialVersionUID = 11790645230L;

	public ErrandOrder() {
		//默认无参构造方法
	}
	// 跑腿订单表主键
	private Long id;

	// 订单编号, max length: 64
	private String orderCode;

	// 跑腿信息表主键
	private Long errandId;

	// 接单者id
	private Long holderId;

	// 发布者id
	private Long publisherId;

	// 支付金额
	private String payMoney;

	// 订单创建时间
//	private Date createTime;

	// 订单完成时间, max length: 20
	private String finishTime;

	// 1-取消订单  2-删除 3-未完成 4-已完成 5-下单失败
//	private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getOrderCode() {
		return this.orderCode;
	}
    public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public Long getErrandId() {
		return this.errandId;
	}
    public void setErrandId(Long errandId) {
		this.errandId = errandId;
	}
	
	public Long getHolderId() {
		return this.holderId;
	}
    public void setHolderId(Long holderId) {
		this.holderId = holderId;
	}
	
	public Long getPublisherId() {
		return this.publisherId;
	}
    public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	
	public String getPayMoney() {
		return this.payMoney;
	}
    public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	
	public String getFinishTime() {
		return this.finishTime;
	}
    public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
}