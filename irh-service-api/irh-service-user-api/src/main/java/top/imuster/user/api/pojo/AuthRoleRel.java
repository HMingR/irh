package top.imuster.user.api.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

import javax.validation.constraints.NotBlank;

/**
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@ApiModel("权限角色管理表")
public class AuthRoleRel extends BaseDomain {

	private static final long serialVersionUID = 6931487658L;

	// 角色权限表主键
	@ApiModelProperty("角色权限表主键")
	private Long id;

	// 角色表中的id
	@ApiModelProperty("角色表中的id")
	@NotBlank(groups = editGroup.class)
	private Long roleId;

	// 权限表中的id
	@ApiModelProperty("权限表中的id")
	@NotBlank(groups = editGroup.class)
	private Long authId;

	// 创建人编号
	@ApiModelProperty("创建人编号")
	private Long createManagementId;

	public AuthRoleRel() {
		//默认无参构造方法
	}

	public interface editGroup{}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
    public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getAuthId() {
		return this.authId;
	}
    public void setAuthId(Long authId) {
		this.authId = authId;
	}
	
	public Long getCreateManagementId() {
		return this.createManagementId;
	}
    public void setCreateManagementId(Long createManagementId) {
		this.createManagementId = createManagementId;
	}
	 
}