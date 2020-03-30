package top.imuster.user.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-03-27 15:53:30
 */
public class UserAuthenRecordInfo extends BaseDomain {

	private static final long serialVersionUID = 7008700647L;

	public UserAuthenRecordInfo() {
		//默认无参构造方法
	}
	// 认证记录表主键
	private Long id;

	// 上传的图片地址, max length: 255
	private String picuri;

	// 认证人
	private Long userId;

	// 1-一卡通认证   2-身份证认证失败之后需要人工认证
	private Integer type;

	// 用户前端输入的名字
	private String inputName;

	private String inputCardNo;

	// 1-认证中  2-认证成功  3-认证失败
	//private Short state;


	public String getInputCardNo() {
		return inputCardNo;
	}

	public void setInputCardNo(String inputCardNo) {
		this.inputCardNo = inputCardNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getPicuri() {
		return this.picuri;
	}
    public void setPicuri(String picuri) {
		this.picuri = picuri;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}