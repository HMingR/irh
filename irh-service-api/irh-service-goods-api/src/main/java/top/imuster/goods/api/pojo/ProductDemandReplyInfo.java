package top.imuster.goods.api.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author 黄明人
 * @since 2020-05-03 15:01:34
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDemandReplyInfo extends BaseDomain {

	private static final long serialVersionUID = 12569429257L;

	public ProductDemandReplyInfo() {
		//默认无参构造方法
	}
	// 需求回复表
	private Long id;

	//  需求表的主键id
	@NotNull(groups = ValidateGroup.addGroup.class)
	private Long demandId;

	//  发布消息的人的id
	private Long userId;

	//  如果type为2，则标识二手商品表的主键id
	@NotNull(groups = ValidateGroup.addGroup.class)
	private Long targetId;

	// 留言内容, max length: 255
	private String content;

	//一级留言下的留言个数
	private Integer childTotal;

	// 1-删除   2-有效  3-被需求发布者选中
	//private Short state;

	public Integer getChildTotal() {
		return childTotal;
	}

	public void setChildTotal(Integer childTotal) {
		this.childTotal = childTotal;
	}

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getDemandId() {
		return this.demandId;
	}
    public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTargetId() {
		return this.targetId;
	}
    public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
}