package top.imuster.user.api.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-18 19:44:30
 */
@ApiModel("静态模板类")
public class IrhTemplate extends BaseDomain {

	private static final long serialVersionUID = 5400267857L;

	public IrhTemplate() {
		//默认无参构造方法
	}
	// 模板表
	@ApiModelProperty("模板表主键")
	private Long id;

	// 模板名称, max length: 255
	@ApiModelProperty("模板名称")
	private String name;

	// 模板描述
	@ApiModelProperty("模板描述")
	private String desc;

	// 模板内容
	@ApiModelProperty("模板内容")
	private String context;

	@ApiModelProperty("1-文章模板  2-商品详情页模板")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
    public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return this.desc;
	}
    public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getContext() {
		return this.context;
	}
    public void setContext(String context) {
		this.context = context;
	}

}