package top.imuster.life.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@ApiModel("文章/帖子表主键")
public class ArticleInfo extends BaseDomain {

	private static final long serialVersionUID = 7095780754L;

	public ArticleInfo() {
		//默认无参构造方法
	}
	// 文章/帖子表主键
	@ApiModelProperty("文章/帖子表主键")
	private Long id;

	// 标题, max length: 255
	@ApiModelProperty("标题")
	private String title;

	// 文章/帖子分类id
	@ApiModelProperty("文章/帖子分类id")
	private Long articleCategory;

	// 封面图片, max length: 255
	@ApiModelProperty("封面图片")
	private String mainPicture;

	// 内容
	@ApiModelProperty("内容")
	private String content;

	//浏览次数
	@ApiModelProperty("浏览次数")
	private Long browserTimes;

	//发布者id
	@ApiModelProperty("发布者id")
	private Long userId;

	@ApiModelProperty("收藏总数")
	private Long collectTotal;

	@ApiModelProperty("点赞总数")
	private Long upTotal;

	@ApiModelProperty("留言总数")
	private Integer reviewTotal;

	//每个文章下的一级留言信息
	@ApiModelProperty("每个文章下的一级留言信息")
	private List<ArticleReview> childs;

	private List<ArticleTag> tagList;

	@ApiModelProperty("给文章添加的标签")
	private String tagIds;

	private Long forwardTimes;

	private List<String> tagNames;

//	@ApiModelProperty("文章标签的名字")
//	private String tagNames;

	@ApiModelProperty("文章详情页url")
	private String detailPage;

	private Long score;

	// 1-无效  2-有效
//	private Short state;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(Long articleCategory) {
		this.articleCategory = articleCategory;
	}

	public String getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getBrowserTimes() {
		return browserTimes;
	}

	public void setBrowserTimes(Long browserTimes) {
		this.browserTimes = browserTimes;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCollectTotal() {
		return collectTotal;
	}

	public void setCollectTotal(Long collectTotal) {
		this.collectTotal = collectTotal;
	}

	public Long getUpTotal() {
		return upTotal;
	}

	public void setUpTotal(Long upTotal) {
		this.upTotal = upTotal;
	}

	public Integer getReviewTotal() {
		return reviewTotal;
	}

	public void setReviewTotal(Integer reviewTotal) {
		this.reviewTotal = reviewTotal;
	}

	public List<ArticleReview> getChilds() {
		return childs;
	}

	public void setChilds(List<ArticleReview> childs) {
		this.childs = childs;
	}

	public List<ArticleTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<ArticleTag> tagList) {
		this.tagList = tagList;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public Long getForwardTimes() {
		return forwardTimes;
	}

	public void setForwardTimes(Long forwardTimes) {
		this.forwardTimes = forwardTimes;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

	public String getDetailPage() {
		return detailPage;
	}

	public void setDetailPage(String detailPage) {
		this.detailPage = detailPage;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
}