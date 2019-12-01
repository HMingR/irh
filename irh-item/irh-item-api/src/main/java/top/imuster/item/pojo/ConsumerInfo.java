package top.imuster.item.pojo;

import top.imuster.domain.base.BaseDomain;

/**
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class ConsumerInfo extends BaseDomain {

	private static final long serialVersionUID = 23378053182L;

	public ConsumerInfo() {
		//默认无参构造方法
	}
	// 自动生成的id
	private Long id;

	// 年龄
	private Short age;

	// 登录用的邮箱, max length: 50
	private String email;

	// md5加密的32位密码
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

	// 10:注销 20:锁定 30:审核中  40:审核通过
	//private Short state;

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
}