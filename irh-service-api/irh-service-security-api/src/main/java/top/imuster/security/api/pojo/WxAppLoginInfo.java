package top.imuster.security.api.pojo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-05-19 15:28:28
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class WxAppLoginInfo extends BaseDomain {

	private static final long serialVersionUID = 8778280145L;

	public WxAppLoginInfo() {
		//默认无参构造方法
	}
	// 微信小程序登录的主键id
	private Long id;

	// 微信端返回的用户唯一id
	private String openId;

	// max length: 255
	private String sessionKey;

	//  1-无效  2-有效
//	private Short state;

	//  用户表的主键id
	private Long userId;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return this.sessionKey;
	}
    public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
	 
}