package top.imuster.security.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-05-30 17:35:12
 */
public class UserAuthenInfo extends BaseDomain {

	private static final long serialVersionUID = 7062772098L;

	public UserAuthenInfo() {
		//默认无参构造方法
	}
	//  用户认证信息表
	private Long id;

	//  认证类型  1-学生证认证  2-身份证认证
	private Integer type;

	//  user_info表的主键id
	private Long userId;

	// 认证成功之后返回的字符串，json格式, max length: 255
	private String realName;

	private String certificateNum;

	// 1-无效  2-有效
	//private Short state;


	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCertificateNum() {
		return certificateNum;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
}