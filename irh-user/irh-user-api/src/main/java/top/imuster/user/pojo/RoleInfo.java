package top.imuster.user.pojo;

import top.imuster.domain.base.BaseDomain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public class RoleInfo extends BaseDomain {

	private static final long serialVersionUID = 6674856799L;

	public RoleInfo() {
		//默认无参构造方法
	}
	// 角色表的主键
	private Long id;

	// 权限名称, max length: 255
	private String roleName;

	// 权限描述
	private String roleDesc;

	// 状态 1:无效  2:有效
	// private Short state;

	// 创建人id
	private Long createManagementId;

	//角色对应的权限
	private List<AuthInfo> authInfoList;

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
	
	public Long getCreateManagementId() {
		return this.createManagementId;
	}
    public void setCreateManagementId(Long createManagementId) {
		this.createManagementId = createManagementId;
	}

	public List<AuthInfo> getAuthInfoList() {
		return authInfoList;
	}

	public void setAuthInfoList(List<AuthInfo> authInfoList) {
		this.authInfoList = authInfoList;
	}
}