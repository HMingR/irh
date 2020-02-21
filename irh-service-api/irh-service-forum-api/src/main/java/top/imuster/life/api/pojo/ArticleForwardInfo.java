package top.imuster.life.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @author 黄明人
 * @since 2020-02-21 17:23:45
 */
@ApiModel("文章转发实体类")
public class ArticleForwardInfo extends BaseDomain {

	private static final long serialVersionUID = 5594240318L;

	public ArticleForwardInfo() {
		//默认无参构造方法
	}
	// 用户转发表主键
	@ApiModelProperty("用户转发表主键")
	private Long id;

	// 转发人的id
	@ApiModelProperty("转发人的id")
	private Long userId;

	// 文章id
	@ApiModelProperty("文章id")
	private Long articleId;

	@ApiModelProperty("评论")
	private String content;

	public ArticleForwardInfo(Long id){
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// 1-无效  2-有效
//	private Short state;

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
	
	public Long getArticleId() {
		return this.articleId;
	}
    public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
}