package top.imuster.life.api.pojo;

import top.imuster.common.base.domain.BaseDomain;

import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
public class ArticleTagRel extends BaseDomain {

	private static final long serialVersionUID = 1438321107L;

	public ArticleTagRel() {
		//默认无参构造方法
	}
	// 帖子标签关系表
	private Long id;

	// 标签id
	private Long tagId;

	// 文章id
	private Long articleId;

	//查询条件用
	private List<Long> tagIds;

	public ArticleTagRel(List<Long> tagIds){
		this.tagIds = tagIds;
	}

	public List<Long> getTagIds() {
		return tagIds;
	}

	public void setTagIds(List<Long> tagIds) {
		this.tagIds = tagIds;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTagId() {
		return this.tagId;
	}
    public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
	public Long getArticleId() {
		return this.articleId;
	}
    public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	 
}