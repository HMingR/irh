package top.imuster.goods.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@ApiModel("商品实体类")
@Document(indexName = "goods", type = "goods")
public class ProductInfo extends BaseDomain {

	private static final long serialVersionUID = 18274143046L;

	public ProductInfo() {
		//默认无参构造方法
	}
	// 商品表的主键
	@ApiModelProperty("商品表的主键id")
	@NotNull(groups = ValidateGroup.editGroup.class)
	private Long id;

	// 商品名称, max length: 255
	@ApiModelProperty("商品名称")
	@NotNull(groups = ValidateGroup.releaseGroup.class, message = "商品名称不能为空")
	private String title;

	// 商品主图url
	@ApiModelProperty("商品主图url")
	private String mainPicUrl;

	// 计量单位, max length: 255
	@ApiModelProperty("计量单位")
	private String unit;

	// 商品原价
	@ApiModelProperty("商品原价")
	private String originalPrice;

	// 售卖价格
	@ApiModelProperty("售卖价格")
	@NotNull(groups = ValidateGroup.releaseGroup.class, message = "售卖价格不能为空")
	private String salePrice;

	// 商品的新旧程度,1~10数值越大越新
	@ApiModelProperty("商品的新旧程度,1~10数值越大越新")
	@NotNull(groups = ValidateGroup.releaseGroup.class, message = "商品的新旧程度不能为空")
	private Integer oldDegree;

	// 商品描述
	@ApiModelProperty("商品描述")
	@NotNull(groups = ValidateGroup.releaseGroup.class, message = "商品描述不能为空")
	private String productDesc;

	// 商品详情页, max length: 255
	@ApiModelProperty("商品详情页")
	private String productDetailsPage;

	// 10-正常交易  20-公益捐赠
	@ApiModelProperty("10-正常交易  20-公益捐赠")
	@NotNull(groups = ValidateGroup.releaseGroup.class, message = "交易类型不能为空")
	private Integer tradeType;

	// 分类id
	@ApiModelProperty("分类id")
	@NotNull(groups = ValidateGroup.releaseGroup.class, message = "分类不能为空")
	private Long categoryId;

	//更新分类时存放新的category
	private Long newCategoryId;

	//出售商品的人
	@ApiModelProperty("出售商品的人")
	private Long consumerId;

	@ApiModelProperty("浏览次数")
	private Long browserTimes;

	private String tagNames;

	//其他图片的url
	private String otherImgUrl;

	//浏览时间
	private String browseDate;

	private Integer collectTotal;

	//商品的版本号
	private Integer version;

	//state 1-无效 2-有效 3-锁定 4-卖出  5-审核中


	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getCollectTotal() {
		return collectTotal;
	}

	public void setCollectTotal(Integer collectTotal) {
		this.collectTotal = collectTotal;
	}

	public String getBrowseDate() {
		return browseDate;
	}

	public void setBrowseDate(String browseDate) {
		this.browseDate = browseDate;
	}

	public String getOtherImgUrl() {
		return otherImgUrl;
	}

	public void setOtherImgUrl(String otherImgUrl) {
		this.otherImgUrl = otherImgUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public Long getBrowserTimes() {
		return browserTimes;
	}

	public void setBrowserTimes(Long browserTimes) {
		this.browserTimes = browserTimes;
	}

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}

	public Long getNewCategoryId() {
		return newCategoryId;
	}

	public void setNewCategoryId(Long newCategoryId) {
		this.newCategoryId = newCategoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	
	public Integer getOldDegree() {
		return this.oldDegree;
	}
    public void setOldDegree(Integer oldDegree) {
		this.oldDegree = oldDegree;
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
	
	public Integer getTradeType() {
		return this.tradeType;
	}
    public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	
}