package top.imuster.forum.api.pojo;

import top.imuster.common.base.domain.BaseDomain;

import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public class ArticleCategory extends BaseDomain {

	private static final long serialVersionUID = 5839629083L;

	public ArticleCategory() {
		//默认无参构造方法
	}
	// 文章分类表主键
	private Long id;

	// 分类名称, max length: 255
	private String name;

	// 父分类id，0标识一级分类
	private Long parentId;

	//子节点
	private List<ArticleCategory> childs;

	// 1-无效  2-有效
//	private Short state;


	public List<ArticleCategory> getChilds() {
		return childs;
	}

	public void setChilds(List<ArticleCategory> childs) {
		this.childs = childs;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
    public void setName(String name) {
		this.name = name;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}