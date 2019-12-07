package top.imuster.user.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.domain.base.BaseDomain;

import java.util.List;

/**
 * 
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@ApiModel("管理人员实体类")
public class ManagementInfo extends BaseDomain {

	private static final long serialVersionUID = 4192808455L;

	// 管理人员表主键
	@ApiModelProperty("管理人员表主键id")
	private Long id;

	// 姓名, max length: 255
	@ApiModelProperty("姓名")
	private String name;

	// 10:服务人员 20:校园组织 25:公益组织 30:校园社团 40:管理员
	@ApiModelProperty("10:服务人员 20:校园组织 25:公益组织 30:校园社团 40:管理员")
	private Integer type;

	// 描述, max length: 1000
	@ApiModelProperty("描述")
	private String desc;

	// 登录密码
	@ApiModelProperty("登录密码")
	private String password;

	//管理员对应的角色
	@ApiModelProperty("管理员对应的角色")
	private List<RoleInfo> roleList;

	// 10:注销 20:锁定 30:审核中 40:审核通过
	// private Short state;


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
	
	public Integer getType() {
		return this.type;
	}
    public void setType(Integer type) {
		this.type = type;
	}
	
	public String getDesc() {
		return this.desc;
	}
    public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

}