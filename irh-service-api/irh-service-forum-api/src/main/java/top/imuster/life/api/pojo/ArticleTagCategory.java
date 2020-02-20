package top.imuster.life.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-13 17:26:48
 */
public class ArticleTagCategory extends BaseDomain {

	private static final long serialVersionUID = 3766927279L;

	public ArticleTagCategory() {
		//默认无参构造方法
	}
	// 文章/帖子标签分类表
	private Long id;

	// 分类名称, max length: 255
	private String name;

	// 1-无效 2-有效
//	private Short state;

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
}