package top.imuster.user.api.pojo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;

/**
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
@ApiModel("兴趣标签实体类")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class InterestTagInfo extends BaseDomain {

	private static final long serialVersionUID = 5497047741L;

	public InterestTagInfo() {
		//默认无参构造方法
	}

	// 兴趣标签表主键
	@ApiModelProperty("兴趣标签表主键")
	@NotEmpty(groups = ValidateGroup.editGroup.class, message = "修改标签的id不能为空")
	private Long id;

	// 标签名称, max length: 255
	@ApiModelProperty("标签名称")
	@NotEmpty(groups = ValidateGroup.addGroup.class, message = "添加的标签名称不能为空")
	private String tagName;

	// 创建者的id,对应于管理员表中的主键id
	@ApiModelProperty("创建者的id")
	private Long managerId;

	// 被该标签标记的总数
	@ApiModelProperty("被该标签标记的总数")
	private Long total;

	@ApiModelProperty("用户是否拥有该标签")
	private Integer available;

	public Integer getAvaliable() {
		return available;
	}

	public void setAvaliable(Integer available) {
		this.available = available;
	}

	// 状态:1-无效 2-有效
	//private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getTagName() {
		return this.tagName;
	}
    public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public Long getManagerId() {
		return this.managerId;
	}
    public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
}