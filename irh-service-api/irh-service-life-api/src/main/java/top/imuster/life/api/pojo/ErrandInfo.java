package top.imuster.life.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;

/**
 * 
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
@ApiModel("跑腿信息实体类")
public class ErrandInfo extends BaseDomain {

	private static final long serialVersionUID = 6877499957L;

	public ErrandInfo() {
		//默认无参构造方法
	}
	// 跑腿表主键
	@ApiModelProperty("跑腿表主键")
	private Long id;

	// 发布者id
	@ApiModelProperty("发布者id")
	private Long publisherId;

	// 服务内容, max length: 255
	@ApiModelProperty("服务内容")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "内容不能为空")
	private String content;

	// 要求
	@ApiModelProperty("要求")
	private String requirement;

	// 价钱
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "价钱不能为空")
	@ApiModelProperty("价钱")
	private String money;


	// 1-删除 2-已完成 3-已接单 4-未接单
//	private Short state;


	@Override
	public String toString() {
		return "ErrandInfo{" +
				"id=" + id +
				", publisherId=" + publisherId +
				", content='" + content + '\'' +
				", requirement='" + requirement + '\'' +
				", money='" + money + '\'' +
				'}';
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
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
	public String getRequirement() {
		return this.requirement;
	}
    public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	
	public String getMoney() {
		return this.money;
	}
    public void setMoney(String money) {
		this.money = money;
	}
	
}