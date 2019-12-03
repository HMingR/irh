package top.imuster.user.pojo;

import top.imuster.domain.base.BaseDomain;

import java.util.Date;

/**
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public class AuthRoleRel extends BaseDomain {

	private static final long serialVersionUID = 6931487658L;

	// 角色权限表主键
	private Long id;

	// 角色表中的id
	private Long roleId;

	// 权限表中的id
	private Long authId;

	// 创建人编号
	private Long createManagementId;

	public AuthRoleRel() {
		//默认无参构造方法
	}

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