package top.imuster.mall.domain;

import java.util.Date;

import top.imuster.domain.base.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public class ConsumerInterestTagRel extends BaseDomain {

	private static final long serialVersionUID = 5983772176L;

	public ConsumerInterestTagRel() {
		//默认无参构造方法
	}
	private Long id;

	// 用户表的主键id
	private Long consumerId;

	// 标签表的主键id
	private Long tagId;

	// 创建时间
	private Date createTime;

	// 最后一次修改时间
	private Date updateTime;

	// 评分
	private Short score;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getConsumerId() {
		return this.consumerId;
	}
    public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}
	
	public Long getTagId() {
		return this.tagId;
	}
    public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
    public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
    public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Short getScore() {
		return this.score;
	}
    public void setScore(Short score) {
		this.score = score;
	}
	 
}