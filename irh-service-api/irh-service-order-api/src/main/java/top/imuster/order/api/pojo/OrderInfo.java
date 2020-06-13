package top.imuster.order.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@ApiModel("订单实体类")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderInfo extends BaseDomain {

	private static final long serialVersionUID = 18856694430L;

	public OrderInfo() {
		//默认无参构造方法
	}
	// 订单表主键
	@ApiModelProperty("订单表主键")
    @NotNull(groups = ValidateGroup.prePayment.class, message = "订单主键不能为空")
	private Long id;

	//订单编号,必须保证唯一,且64位之内64个字符以内,只能包含字母、数字、下划线
	@ApiModelProperty("订单编号")
	@NotEmpty(groups = ValidateGroup.prePayment.class, message = "订单编号不能为空")
	private String orderCode;

	// 卖家的id
	@ApiModelProperty("卖家的id")
	@NotNull(groups = ValidateGroup.prePayment.class, message = "卖家的id不能为空")
	private Long salerId;

	private String salerNickname;

	// 会员表的id
	@ApiModelProperty("会员表的id")
	@NotNull(groups = ValidateGroup.queryGroup.class, message = "会员的id不能为空")
	private Long buyerId;

	// 商品id
	@ApiModelProperty("商品id")
	@NotNull(groups = ValidateGroup.prePayment.class, message = "商品id不能为空")
	private Long productId;

	// 支付金额
	@ApiModelProperty("支付金额")
	@NotNull(groups = ValidateGroup.prePayment.class, message = "支付金额不能为空")
	private String paymentMoney;

	//paymentMoney
	private double money;

	// 订单备注, max length: 1000
	@ApiModelProperty("订单备注, max length: 1000")
	@NotNull(groups = ValidateGroup.prePayment.class, message = "订单编号不能为空")
	private String orderRemark;

	// 送货地址:将楼号、楼层、宿舍号以json格式存储
	@ApiModelProperty("送货地址:将楼号、楼层、宿舍号以json格式存储")
	private String address;

	// 10:线上交易 20:线下交易 30:公益捐赠
	@ApiModelProperty("10:正常交易  20:公益捐赠")
	private Integer tradeType;

	// 支付时间
	@ApiModelProperty("支付时间")
	private Date paymentTime;

	// 交易完成时间,用户确定收货的时间
	@ApiModelProperty("交易完成时间,用户确定收货的时间")
	private Date finishTime;

	@ApiModelProperty("订单版本，默认为1，当修改订单的时候会将版本号加1")
	private Integer orderVersion;

	private String phoneNum;

	//是否评价
	private Long evaluateId;

	@NotEmpty(groups = ValidateGroup.prePayment.class, message = "商品版本信息不能为空")
	private Integer productVersion;

	public Integer getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(Integer productVersion) {
		this.productVersion = productVersion;
	}

	// 10:订单超时 20:取消订单 30:买家删除订单 35:卖家删除订单  40:等待支付 50:交易成功 60:捐款金额已分配
	@ApiModelProperty("10:订单超时 20:取消订单 40:等待支付 50:交易成功 60:买家删除订单  70:卖家删除订单  80:评价  90:追评  100:捐款金额已分配 ")
	//private Short state;

	//查询时需要
	private String minMoney;

	private String maxMoney;

	public String getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(String minMoney) {
		this.minMoney = minMoney;
	}

	public String getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSalerNickname() {
		return salerNickname;
	}

	public void setSalerNickname(String salerNickname) {
		this.salerNickname = salerNickname;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Integer getOrderVersion() {
		return orderVersion;
	}

	public void setOrderVersion(Integer orderVersion) {
		this.orderVersion = orderVersion;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

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