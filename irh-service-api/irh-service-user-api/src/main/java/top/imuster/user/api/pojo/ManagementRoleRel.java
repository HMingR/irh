package top.imuster.user.api.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@ApiModel("管理员角色关系实体类")
public class ManagementRoleRel extends BaseDomain {

	private static final long serialVersionUID = 6925444191L;

	public ManagementRoleRel() {
		//默认无参构造方法
	}
	// 管理员角色表的主键
	@ApiModelProperty("角色表的主键")
	private Long id;

	// 工作人员表的id
	@ApiModelProperty("管理员的id")
	private Long staffId;

	// 角色表中的id
	@ApiModelProperty("角色表中的id")
	private Long roleId;

	// 创建人id
	@ApiModelProperty("创建人")
	private String createManagemen;

	// 状态 1:无效 2:有效
	// private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getStaffId() {
		return this.staffId;
	}
    public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}
    public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getCreateManagemen() {
		return createManagemen;
	}

	public void setCreateManagemen(String createManagemen) {
		this.createManagemen = createManagemen;
	}
}