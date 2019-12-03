package top.imuster.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class ConsumerInfo extends User {

	private static final long serialVersionUID = 23378053182L;

	// 自动生成的id
	private Long id;

	// 年龄
	private Short age;

	// 登录用的邮箱, max length: 50
	private String email;

	// md5加密的32位密码
	@JsonIgnore
	private String password;

	// 支付宝账号, max length: 13
	private String alipayNum;

	// 用户昵称
	private String nickname;

	// qq账号, max length: 255
	private String qq;

	// 1-女 2-男
	private Short gender;

	// 手机号, max length: 11
	private String phoneNum;

	// 寝室楼号
	private String buildingNum;

	// 楼层, max length: 255
	private String buildingStorey;

	// 寝室号
	private String dormNum;

	// 真实姓名, max length: 255
	private String realName;

	// 证件号码
	private String certificateNum;

	// 学校名称, max length: 255
	private String schoolName;

	// 学院
	private String academyName;

	// 专业, max length: 255
	private String majorName;

	//创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	//最后更新时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;

	// 10:注销 20:锁定 30:审核中 40:审核通过
	private Integer state;

	private List<RoleInfo> roleList;

	public ConsumerInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Short getAge() {
		return this.age;
	}
    public void setAge(Short age) {
		this.age = age;
	}
	
	public String getEmail() {
		return this.email;
	}
    public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}
    public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAlipayNum() {
		return this.alipayNum;
	}
    public void setAlipayNum(String alipayNum) {
		this.alipayNum = alipayNum;
	}
	
	public String getNickname() {
		return this.nickname;
	}
    public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getQq() {
		return this.qq;
	}
    public void setQq(String qq) {
		this.qq = qq;
	}
	
	public Short getGender() {
		return this.gender;
	}
    public void setGender(Short gender) {
		this.gender = gender;
	}
	
	public String getPhoneNum() {
		return this.phoneNum;
	}
    public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getBuildingNum() {
		return this.buildingNum;
	}
    public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	
	public String getBuildingStorey() {
		return this.buildingStorey;
	}
    public void setBuildingStorey(String buildingStorey) {
		this.buildingStorey = buildingStorey;
	}
	
	public String getDormNum() {
		return this.dormNum;
	}
    public void setDormNum(String dormNum) {
		this.dormNum = dormNum;
	}
	
	public String getRealName() {
		return this.realName;
	}
    public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getCertificateNum() {
		return this.certificateNum;
	}
    public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	
	public String getSchoolName() {
		return this.schoolName;
	}
    public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getAcademyName() {
		return this.academyName;
	}
    public void setAcademyName(String academyName) {
		this.academyName = academyName;
	}
	
	public String getMajorName() {
		return this.majorName;
	}
    public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@JsonIgnore
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (RoleInfo role : roleList) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return state >= 20;
	}

	@Override
	public String getUsername() {
		return nickname;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}