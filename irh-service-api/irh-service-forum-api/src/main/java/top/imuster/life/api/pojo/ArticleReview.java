package top.imuster.life.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@ApiModel("文章评论实体类")
public class ArticleReview extends BaseDomain {

	private static final long serialVersionUID = 8957837652L;

	public ArticleReview() {
		//默认无参构造方法
	}
	// 文章评论表主键
	@ApiModelProperty("文章评论表主键")
	private Long id;

	// 文章编号id
	@ApiModelProperty("文章编号id")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "文章id不能为空")
	private Long articleId;

	// 用户id
	@ApiModelProperty("用户id")
	private Long userId;

	// 回复消息的id，0表示是新的留言的时候
	@ApiModelProperty("回复消息的id，0表示是新的留言的时候")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "回复消息的id不能为空")
	private Long parentId;

	// 内容, max length: 2048
	@ApiModelProperty("内容")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "内容不能为空")
	private String content;

	//一级留言下的回复总数
	@ApiModelProperty("一级留言下的回复总数")
	private Integer childTotalCount;

	//一级留言的id,也就是顶级parent_id,当parent_id为0时该值也为0,当parent_id不为0时,则该值为回复树的顶层回复id
	@ApiModelProperty("一级留言的id,也就是顶级parent_id,当parent_id为0时该值也为0,当parent_id不为0时,则该值为回复树的顶层回复id")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "一级留言的id不能为空")
	private Long firstClassId;

	@ApiModelProperty("点赞总数")
	private Long upTotal;

	//每个留言下的回复数
	private List<ArticleReview> childs;

	//父id的作者名
	private Long parentWriterId;

	// 1-无效 2-有效
//	private Short state;


	public Long getParentWriterId() {
		return parentWriterId;
	}

	public void setParentWriterId(Long parentWriterId) {
		this.parentWriterId = parentWriterId;
	}

	public Long getUpTotal() {
		return upTotal;
	}

	public void setUpTotal(Long upTotal) {
		this.upTotal = upTotal;
	}

	public Long getFirstClassId() {
		return firstClassId;
	}

	public void setFirstClassId(Long firstClassId) {
		this.firstClassId = firstClassId;
	}

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