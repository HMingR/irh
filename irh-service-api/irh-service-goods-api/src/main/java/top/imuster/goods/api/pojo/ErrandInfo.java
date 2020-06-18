package top.imuster.life.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;

/**
 * 
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
@ApiModel("跑腿信息实体类")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrandInfo extends BaseDomain {

	private static final long serialVersionUID = 6877499957L;

	public ErrandInfo() {
		//默认无参构造方法
	}
	// 跑腿表主键
	@ApiModelProperty("跑腿表主键")
	private Long id;

	// 发布者id
	@ApiModelProperty("发布者id")
	private Long publisherId;

	// 服务内容, max length: 255
	@ApiModelProperty("服务内容")
	@NotEmpty(groups = ValidateGroup.addGroup.class, message = "内容不能为空")
	private String content;

	// 要求
	@ApiModelProperty("要求")
	private String requirement;

	// 价钱
	@NotEmpty(groups = ValidateGroup.addGroup.class, message = "价钱不能为空")
	@ApiModelProperty("价钱")
	private String money;

	//版本
	private Integer version;

	//送货地址
	private String address;

	//接收方电话
	private String phoneNum;

	//凭证  如取快递的时候需要取件密码
	private String cypher;

	//支付状态  0-未支付  1-已支付
	private Integer payState;

	// 1-删除 2-有效 3-已被接单
//	private Short state;


	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public String getCypher() {
		return cypher;
	}

	public void setCypher(String cypher) {
		this.cypher = cypher;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPublisherId() {
		return this.publisherId;
	}
    public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
	public String getRequirement() {
		return this.requirement;
	}
    public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	
	public String getMoney() {
		return this.money;
	}
    public void setMoney(String money) {
		this.money = money;
	}
	
}