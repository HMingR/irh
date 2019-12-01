package top.imuster.item.domain;
import java.util.Date;

import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class OrderInfo extends BaseDomain {

	private static final long serialVersionUID = 18856694430L;

	public OrderInfo() {
		//默认无参构造方法
	}
	// 订单表主键
	private Long id;

	// 会员表的id
	private Long salerId;

	// 会员表的nickname字段, max length: 255
	private String salerNickname;

	// 会员表的id
	private Long buyerId;

	// 商品id
	private Long productId;

	// 支付金额
	private String paymentMoney;

	// 订单留言, max length: 1000
	private String orderRemark;

	// 送货地址:将楼号、楼层、宿舍号以json格式存储
	private String address;

	// 10:线上交易 20:线下交易 30:公益捐赠
	private Integer tradeType;

	// 支付时间
	private Date paymentTime;

	// 交易完成时间,用户确定收货的时间
	private Date finishTime;

	// 10:订单超时 20:取消订单 30:删除订单 40:交易成功
	//private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSalerId() {
		return this.salerId;
	}
    public void setSalerId(Long salerId) {
		this.salerId = salerId;
	}
	
	public String getSalerNickname() {
		return this.salerNickname;
	}
    public void setSalerNickname(String salerNickname) {
		this.salerNickname = salerNickname;
	}
	
	public Long getBuyerId() {
		return this.buyerId;
	}
    public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public Long getProductId() {
		return this.productId;
	}
    public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getPaymentMoney() {
		return this.paymentMoney;
	}
    public void setPaymentMoney(String paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	
	public String getOrderRemark() {
		return this.orderRemark;
	}
    public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
	public String getAddress() {
		return this.address;
	}
    public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getTradeType() {
		return this.tradeType;
	}
    public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	
	public Date getPaymentTime() {
		return this.paymentTime;
	}
    public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public Date getFinishTime() {
		return this.finishTime;
	}
    public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
}