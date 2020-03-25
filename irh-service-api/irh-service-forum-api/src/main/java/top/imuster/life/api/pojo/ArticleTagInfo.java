package top.imuster.life.api.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@ApiModel("帖子分类实体")
public class ArticleTagInfo extends BaseDomain {

	private static final long serialVersionUID = 5839629083L;

	public ArticleTagInfo() {
		//默认无参构造方法
	}
	// 文章分类表主键
	@ApiModelProperty("文章分类表主键")
	private Long id;

	// 分类名称, max length: 255
	@ApiModelProperty("分类名称")
	private String name;

	// 父分类id，0标识一级分类
	@ApiModelProperty("分类id")
	private Long categoryId;

	@ApiModelProperty("用户是否拥有该标签 0-为拥有 1-拥有")
	private Integer available;

	public ArticleTagInfo(Long categoryId) {
		this.categoryId = categoryId;
	}

	// 1-无效  2-有效
//	private Short state;


	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}