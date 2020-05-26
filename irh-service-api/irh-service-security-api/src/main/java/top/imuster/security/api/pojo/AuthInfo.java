package top.imuster.security.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@ApiModel("权限信息")
public class AuthInfo extends BaseDomain {

	private static final long serialVersionUID = 9096740427L;

	// 权限表中的主键
	@NotEmpty(groups = ValidateGroup.editGroup.class, message = "参数异常")
	@ApiModelProperty(value = "权限表中的主键",notes = "修改权限时必须要有")
	private Long id;

	// 父权限id
	@ApiModelProperty("父权限id")
	private Long parentId;

	// 权限名称, max length: 255
	@ApiModelProperty(value = "权限名称", notes = "添加时必须要有")
	@NotEmpty(groups = ValidateGroup.addGroup.class, message = "参数异常")
	private String authName;

	// 权限描述
	@ApiModelProperty(value = "权限描述", notes = "添加时必须要有")
	@NotEmpty(groups = ValidateGroup.addGroup.class, message = "参数异常")
	private String authDesc;

	@ApiModelProperty(value = "该url的访问Http方式")
	private String httpMethod;

	private List<AuthInfo> childs = new ArrayList<>();

	//当是查看角色的权限时，拥有则为1，为拥有则为0
	private Integer have;

	// 状态 1:无效 2:有效
	// private Short state;

	public AuthInfo() {
		//默认无参构造方法
	}

	public List<AuthInfo> getChilds() {
		return childs;
	}

	public void setChilds(List<AuthInfo> childs) {
		this.childs = childs;
	}

	public Integer getHave() {
		return have;
	}

	public void setHave(Integer have) {
		this.have = have;
	}

	@Override
	public String toString() {
		return "AuthInfo{" +
				"id=" + id +
				", parentId=" + parentId +
				", authName='" + authName + '\'' +
				", authDesc='" + authDesc + '\'' +
				'}';
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getAuthName() {
		return this.authName;
	}
    public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	public String getAuthDesc() {
		return this.authDesc;
	}
    public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}
}