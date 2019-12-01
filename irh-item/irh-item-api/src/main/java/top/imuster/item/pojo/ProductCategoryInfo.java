package top.imuster.item.pojo;

import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class ProductCategoryInfo extends BaseDomain {

	private static final long serialVersionUID = 6182073821L;

	public ProductCategoryInfo() {
		//默认无参构造方法
	}
	// 商品分类信息表的主键
	private Long id;

	// 当值为0的时候标识根节点
	private Long parentId;

	// 分类名称, max length: 255
	private String name;

	// 分类的描述
	private String desc;

	// 1:无效  2:有效
	//private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return this.name;
	}
    public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}
    public void setDesc(String desc) {
		this.desc = desc;
	}
}