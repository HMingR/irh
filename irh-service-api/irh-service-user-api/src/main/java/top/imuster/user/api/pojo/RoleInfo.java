package top.imuster.user.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@ApiModel("角色实体类")
public class RoleInfo extends BaseDomain {

	private static final long serialVersionUID = 6674856799L;

	public RoleInfo() {
		//默认无参构造方法
	}
	// 角色表的主键
	@ApiModelProperty("角色表的主键")
	@NotBlank(groups = {ValidateGroup.editGroup.class},message = "角色id不能为空")
	private Long id;

	// 角色名称, max length: 255
	@ApiModelProperty("角色名称")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "角色名称不能为空")
	private String roleName;

	// 角色描述
	@ApiModelProperty("角色描述")
	@NotBlank(groups = ValidateGroup.addGroup.class, message = "角色描述不能为空")
	private String roleDesc;

	// 状态 1:无效  2:有效
	// private Short state;

	// 创建人姓名
	@ApiModelProperty("创建人id")
	private String createManagement;

	//角色对应的权限
	private List<AuthInfo> authInfoList;

	@Override
	public String toString() {
		return "RoleInfo{" +
				"id=" + id +
				", roleName='" + roleName + '\'' +
				", roleDesc='" + roleDesc + '\'' +
				", createManagement=" + createManagement +
				'}';
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
    public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleDesc() {
		return this.roleDesc;
	}
    public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public String getCreateManagement() {
		return this.createManagement;
	}
    public void setCreateManagement(String createManagementId) {
		this.createManagement = createManagement;
	}

	public List<AuthInfo> getAuthInfoList() {
		return authInfoList;
	}

	public void setAuthInfoList(List<AuthInfo> authInfoList) {
		this.authInfoList = authInfoList;
	}
}