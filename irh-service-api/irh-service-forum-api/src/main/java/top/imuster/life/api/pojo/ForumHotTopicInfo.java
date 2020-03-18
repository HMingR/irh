package top.imuster.life.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
@ApiModel("文章热搜表")
public class ForumHotTopicInfo extends BaseDomain {

	private static final long serialVersionUID = 4870108998L;

	public ForumHotTopicInfo() {
		//默认无参构造方法
	}
	// 文章模块热搜表
	@ApiModelProperty("文章模块热搜表")
	private Long id;

	// 目标id
	@ApiModelProperty("目标id")
	private Long targetId;

	// redis中存储的score
	@ApiModelProperty("redis中存储的score,即热度")
	private Long score;

	@ApiModelProperty("目标文章的标题")
	private String targetTitle;

	@ApiModelProperty("转发次数")
	private Long forwardTimes;

	private Long browserTimes;

	@ApiModelProperty("留言总数")
	private Long reviewTotal;

	@ApiModelProperty("点赞次数")
	private Long upTotal;

	@ApiModelProperty("主图url")
	private String mainPicUrl;

	@ApiModelProperty("作者id")
	private Long userId;

	// 1-有效  2-无效
//	private Short state;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReviewTotal() {
		return reviewTotal;
	}

	public void setReviewTotal(Long reviewTotal) {
		this.reviewTotal = reviewTotal;
	}

	public Long getUpTotal() {
		return upTotal;
	}

	public void setUpTotal(Long upTotal) {
		this.upTotal = upTotal;
	}

	public Long getForwardTimes() {
		return forwardTimes;
	}

	public void setForwardTimes(Long forwardTimes) {
		this.forwardTimes = forwardTimes;
	}

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public Long getBrowserTimes() {
		return browserTimes;
	}

	public void setBrowserTimes(Long browserTimes) {
		this.browserTimes = browserTimes;
	}

	public String getTargetTitle() {
		return targetTitle;
	}

	public void setTargetTitle(String targetTitle) {
		this.targetTitle = targetTitle;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTargetId() {
		return this.targetId;
	}
    public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	
	public Long getScore() {
		return this.score;
	}
    public void setScore(Long score) {
		this.score = score;
	}
}