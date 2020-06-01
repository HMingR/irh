package top.imuster.life.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@ApiModel("文章/帖子表主键")
@Document(indexName = "article", type = "article")
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
	@NotEmpty(groups = ValidateGroup.addGroup.class, message = "标题不能为空")
	private String title;

	// 封面图片, max length: 255
	@ApiModelProperty("封面图片")
	private String mainPicture;

	// 内容
	@ApiModelProperty("内容")
	@NotEmpty(groups = ValidateGroup.addGroup.class, message = "内容不能为空")
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
	private List<ArticleReviewInfo> childs;

	@ApiModelProperty("给文章添加的标签")
	private String tagNames;

	private Long forwardTimes;

	@ApiModelProperty("文章详情页url")
	private String detailPage;

	//热搜指数
	private Long score;

	//分类id
	private Long categoryId;

	//文章简介  一般为文章前50个非html和非空白字符
	private String articleSummary;

	// 1-无效  2-有效 3-发布中  4-草稿箱中
//	private Short state;


	public String getArticleSummary() {
		return articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

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

	public List<ArticleReviewInfo> getChilds() {
		return childs;
	}

	public void setChilds(List<ArticleReviewInfo> childs) {
		this.childs = childs;
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public Long getForwardTimes() {
		return forwardTimes;
	}

	public void setForwardTimes(Long forwardTimes) {
		this.forwardTimes = forwardTimes;
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