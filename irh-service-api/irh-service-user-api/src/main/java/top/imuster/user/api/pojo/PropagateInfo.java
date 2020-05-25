package top.imuster.user.api.pojo;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
public class PropagateInfo extends BaseDomain {

	private static final long serialVersionUID = 11936859429L;

	public PropagateInfo() {
		//默认无参构造方法
	}
	// 广告表和通知的主键id
	private Long id;

	//  发布者id
	private Long publisherId;

	//  审核人
	private Long approverId;

	// 广告标题, max length: 255
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "")
	private String title;

	// 内容
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "")
	private String content;

	// 标签,多个用'|'分隔, max length: 255
	private String tagNames;

	// 1-广告   2-学校通知
	@NotNull(groups = ValidateGroup.addGroup.class, message = "")
	private Integer type;

	// 1-删除 2-有效
	//private Short state;

	// 浏览次数
	private Integer browseTimes;

	// 静态页面地址, max length: 255
	private String detailPageUri;

	private String mainPic;

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPublisherId() {
		return this.publisherId;
	}
    public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	
	public Long getApproverId() {
		return this.approverId;
	}
    public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	
	public String getTitle() {
		return this.title;
	}
    public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
	public String getTagNames() {
		return this.tagNames;
	}
    public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}
	
	public Integer getBrowseTimes() {
		return this.browseTimes;
	}
    public void setBrowseTimes(Integer browseTimes) {
		this.browseTimes = browseTimes;
	}
	
	public String getDetailPageUri() {
		return this.detailPageUri;
	}
    public void setDetailPageUri(String detailPageUri) {
		this.detailPageUri = detailPageUri;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}