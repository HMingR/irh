package top.imuster.life.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
public class UserForumAttribute extends BaseDomain {

	private static final long serialVersionUID = 6844631641L;

	public UserForumAttribute() {
		//默认无参构造方法
	}
	// 用户论坛点赞表主键
	private Long id;

	// 用户id
	private Long userId;

	// 点赞对象id
	private Long targetId;

	// 1-文章   2-评论
	private Integer type;

	// 1-无效 2-有效
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
	
	public Long getTargetId() {
		return this.targetId;
	}
    public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}