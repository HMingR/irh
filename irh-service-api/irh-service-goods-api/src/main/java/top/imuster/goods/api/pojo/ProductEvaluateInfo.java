package top.imuster.goods.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@ApiModel("商品评价实体类")
public class ProductEvaluateInfo extends BaseDomain {

	private static final long serialVersionUID = 16463337443L;

	public ProductEvaluateInfo() {
		//默认无参构造方法
	}
	// 商品评价表id
	@ApiModelProperty("商品评价表id")
	private Long id;

	// 商品id
	@ApiModelProperty("商品id")
	private Long productId;

	// 商品名称, max length: 255
	@ApiModelProperty("商品名称")
	private String productName;

	// 买家编号
	@ApiModelProperty("买家编号")
	private Long buyerId;

	// 卖家id
	@ApiModelProperty("卖家id")
	private Long salerId;

	// 订单id
	@ApiModelProperty("订单id")
	@NotEmpty(groups = ValidateGroup.evaluateGroup.class, message = "评价商品的订单编号不能为空")
	private Long orderId;

	// 内容, max length: 1000
	@ApiModelProperty("内容")
	private String content;

	// 对商品质量的评价,0~10个等级
	@ApiModelProperty("对商品质量的评价,0~10个等级")
	private Integer productQualityEvaluate;

	// 对卖家服务的评价,0~10个等级
	@ApiModelProperty("对卖家服务的评价,0~10个等级")
	private Integer salerServiceEvaluate;

	// 整体评价等级,0~10个等级
	@ApiModelProperty("整体评价等级,0~10个等级")
	private Integer wholeEvaluate;

	// 1-无效 2-有效
	//private Short state;

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
	
	public Integer getProductQualityEvaluate() {
		return this.productQualityEvaluate;
	}
    public void setProductQualityEvaluate(Integer productQualityEvaluate) {
		this.productQualityEvaluate = productQualityEvaluate;
	}
	
	public Integer getSalerServiceEvaluate() {
		return this.salerServiceEvaluate;
	}
    public void setSalerServiceEvaluate(Integer salerServiceEvaluate) {
		this.salerServiceEvaluate = salerServiceEvaluate;
	}
	
	public Integer getWholeEvaluate() {
		return this.wholeEvaluate;
	}
    public void setWholeEvaluate(Integer wholeEvaluate) {
		this.wholeEvaluate = wholeEvaluate;
	}

	public Long getSalerId() {
		return salerId;
	}

	public void setSalerId(Long salerId) {
		this.salerId = salerId;
	}
}