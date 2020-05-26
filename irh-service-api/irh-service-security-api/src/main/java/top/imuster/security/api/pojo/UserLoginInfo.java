package top.imuster.security.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-05-26 09:40:11
 */
public class UserLoginInfo extends BaseDomain {

	private static final long serialVersionUID = 7805736402L;

	public UserLoginInfo() {
		//默认无参构造方法
	}
	// 用户登陆表主键
	private Long id;

	//  用户名
	private Long userId;

	// 登录名, max length: 255
	private String loginName;

	// 密码
	private String password;

	// 10:普通会员 20:服务人员  30:校园组织  40:校园社团 50:管理员
	private Integer type;

	// 10:注销 20:锁定 25:未实名 30:审核中  40:审核通过 50:认证失败
//	private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getLoginName() {
		return this.loginName;
	}
    public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPassword() {
		return this.password;
	}
    public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getType() {
		return this.type;
	}
    public void setType(Integer type) {
		this.type = type;
	}
}