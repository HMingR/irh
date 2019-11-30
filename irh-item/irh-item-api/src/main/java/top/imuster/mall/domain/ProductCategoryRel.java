package top.imuster.mall.domain;

import java.util.Date;

import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class ProductCategoryRel extends BaseDomain {

	private static final long serialVersionUID = 6719726738L;

	public ProductCategoryRel() {
		//默认无参构造方法
	}
	// 商品分类表主键
	private Long id;

	// 商品id
	private Long productId;

	// 分类id
	private Long categoryId;

	// 创建时间
	private Date createTime;

	// 最后一次修改时间
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
	
	public Long getCategoryId() {
		return this.categoryId;
	}
    public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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