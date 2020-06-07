package top.imuster.user.api.pojo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@ApiModel("用户兴趣关联表")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserInterestTagRel extends BaseDomain {

	private static final long serialVersionUID = 5983772176L;

	public UserInterestTagRel() {
		//默认无参构造方法
	}
	@ApiModelProperty("用户兴趣关联表主键")
	private Long id;

	// 用户表的主键id
	@ApiModelProperty("用户表的主键id")
	private Long consumerId;

	// 标签表的主键id
	@ApiModelProperty("标签表的主键id")
	private Long tagId;

	// 评分
	@ApiModelProperty("评分")
	private Short score;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getConsumerId() {
		return this.consumerId;
	}
    public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}
	
	public Long getTagId() {
		return this.tagId;
	}
    public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
	public Short getScore() {
		return this.score;
	}
    public void setScore(Short score) {
		this.score = score;
	}
	 
}