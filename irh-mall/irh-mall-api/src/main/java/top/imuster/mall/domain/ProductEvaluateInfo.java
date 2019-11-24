package top.imuster.mall.domain;

import java.util.Date;

import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class ProductEvaluateInfo extends BaseDomain {

	private static final long serialVersionUID = 16463337443L;

	public ProductEvaluateInfo() {
		//默认无参构造方法
	}
	// 商品评价表id
	private Long id;

	// 商品id
	private Long productId;

	// 商品名称, max length: 255
	private String productName;

	// 买家编号
	private Long buyerId;

	// 买家昵称, max length: 255
	private String buyerNickname;

	// 订单id
	private Long orderId;

	// 内容, max length: 1000
	private String content;

	// 对商品质量的评价,0~10个等级
	private Short productQualityEvaluate;

	// 对卖家服务的评价,0~10个等级
	private Short salerServiceEvaluate;

	// 整体评价等级,0~10个等级
	private Short wholeEvaluate;

	private Date createTime;

	// 更新时间
	private Date updateTime;

	// 1-无效 2-有效
	private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProductId() {
		return this.productId;
	}
    public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return this.productName;
	}
    public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Long getBuyerId() {
		return this.buyerId;
	}
    public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getBuyerNickname() {
		return this.buyerNickname;
	}
    public void setBuyerNickname(String buyerNickname) {
		this.buyerNickname = buyerNickname;
	}
	
	public Long getOrderId() {
		return this.orderId;
	}
    public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
	public Short getProductQualityEvaluate() {
		return this.productQualityEvaluate;
	}
    public void setProductQualityEvaluate(Short productQualityEvaluate) {
		this.productQualityEvaluate = productQualityEvaluate;
	}
	
	public Short getSalerServiceEvaluate() {
		return this.salerServiceEvaluate;
	}
    public void setSalerServiceEvaluate(Short salerServiceEvaluate) {
		this.salerServiceEvaluate = salerServiceEvaluate;
	}
	
	public Short getWholeEvaluate() {
		return this.wholeEvaluate;
	}
    public void setWholeEvaluate(Short wholeEvaluate) {
		this.wholeEvaluate = wholeEvaluate;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
    public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Short getState() {
		return this.state;
	}
    public void setState(Short state) {
		this.state = state;
	}
	 
}