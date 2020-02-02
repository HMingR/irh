package top.imuster.forum.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public class ArticleReview extends BaseDomain {

	private static final long serialVersionUID = 8957837652L;

	public ArticleReview() {
		//默认无参构造方法
	}
	// 文章评论表主键
	private Long id;

	// 文章编号id
	private Long articleId;

	// 用户id
	private Long userId;

	// 回复消息的id，0表示是新的留言的时候 
	private Long parentId;

	// 内容, max length: 2048
	private String content;

	//一级留言下的回复总数
	private Integer childTotalCount;

	//每个留言下的回复数
	private List<ArticleReview> childs;

	// 1-无效 2-有效
//	private Short state;


	public Integer getChildTotalCount() {
		return childTotalCount;
	}

	public void setChildTotalCount(Integer childTotalCount) {
		this.childTotalCount = childTotalCount;
	}

	public List<ArticleReview> getChilds() {
		return childs;
	}

	public void setChilds(List<ArticleReview> childs) {
		this.childs = childs;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getArticleId() {
		return this.articleId;
	}
    public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
}