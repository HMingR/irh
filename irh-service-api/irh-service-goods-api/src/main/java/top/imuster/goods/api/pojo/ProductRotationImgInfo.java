package top.imuster.goods.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-06-01 15:45:21
 */
public class ProductRotationImgInfo extends BaseDomain {

	private static final long serialVersionUID = 9682771997L;

	public ProductRotationImgInfo() {
		//默认无参构造方法
	}
	// 商城首页轮播图信息表
	private Long id;

	// 搜索的关键字, max length: 255
	private String param;

	// 图片地址
	private String imgUrl;

	// 1-无效  2-有效
//	private Short state;

	// 点击次数
	private Integer clickTotal;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}
    public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public Integer getClickTotal() {
		return this.clickTotal;
	}
    public void setClickTotal(Integer clickTotal) {
		this.clickTotal = clickTotal;
	}
	 
}