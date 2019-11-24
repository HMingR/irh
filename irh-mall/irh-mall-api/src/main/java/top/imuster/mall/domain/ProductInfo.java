package top.imuster.mall.domain;

import java.util.Date;

import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
public class ProductInfo extends BaseDomain {

	private static final long serialVersionUID = 18274143046L;

	public ProductInfo() {
		//默认无参构造方法
	}
	// 商品表的主键
	private Long id;

	// 商品名称, max length: 255
	private String productName;

	// 商品主图url
	private String mainPicUrl;

	// 计量单位, max length: 255
	private String unit;

	// 商品原价
	private String originalPrice;

	// 售卖价格
	private String salePrice;

	// 商品的新旧程度,1~10数值越大越新
	private Short oldDegree;

	// 商品标题, max length: 255
	private String productTitle;

	// 商品描述
	private String productDesc;

	// 商品详情页, max length: 255
	private String productDetailsPage;

	// 10-正常交易  20-公益捐赠
	private Short tradeType;

	// 父级分类id
	private Long parentCategoryId;

	// 创建时间
	private Date createTime;

	// 最后一次修改时间
	private Date updateTime;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getProductName() {
		return this.productName;
	}
    public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getMainPicUrl() {
		return this.mainPicUrl;
	}
    public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	
	public String getUnit() {
		return this.unit;
	}
    public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getOriginalPrice() {
		return this.originalPrice;
	}
    public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public String getSalePrice() {
		return this.salePrice;
	}
    public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	
	public Short getOldDegree() {
		return this.oldDegree;
	}
    public void setOldDegree(Short oldDegree) {
		this.oldDegree = oldDegree;
	}
	
	public String getProductTitle() {
		return this.productTitle;
	}
    public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	
	public String getProductDesc() {
		return this.productDesc;
	}
    public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	
	public String getProductDetailsPage() {
		return this.productDetailsPage;
	}
    public void setProductDetailsPage(String productDetailsPage) {
		this.productDetailsPage = productDetailsPage;
	}
	
	public Short getTradeType() {
		return this.tradeType;
	}
    public void setTradeType(Short tradeType) {
		this.tradeType = tradeType;
	}
	
	public Long getParentCategoryId() {
		return this.parentCategoryId;
	}
    public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
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
	 
}