package top.imuster.goods.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-04-26 15:48:44
 */
public class ProductCollectRel extends BaseDomain {

	private static final long serialVersionUID = 8953005972L;

	public ProductCollectRel() {
		//默认无参构造方法
	}
	// 商品收藏表主键
	private Long id;

	// 用户id
	private Long userId;

	// 商品id
	private Long productId;

	//1-收藏商品  2-收藏需求
	private Integer type;


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getProductId() {
		return this.productId;
	}
    public void setProductId(Long productId) {
		this.productId = productId;
	}
}