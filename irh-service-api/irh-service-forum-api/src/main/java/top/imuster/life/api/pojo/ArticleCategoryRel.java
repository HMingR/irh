package top.imuster.life.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import top.imuster.common.base.domain.BaseDomain;

import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ArticleCategoryRel extends BaseDomain {

	private static final long serialVersionUID = 1438321107L;

	public ArticleCategoryRel() {
		//默认无参构造方法
	}
	// 帖子标签关系表
	private Long id;

	// 分类id
	private Long categoryId;

	// 文章id
	private Long articleId;

	//查询条件用
	private List<Long> categoryIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}
}