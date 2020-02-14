package top.imuster.forum.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
public class ForumHotTopic extends BaseDomain {

	private static final long serialVersionUID = 4870108998L;

	public ForumHotTopic() {
		//默认无参构造方法
	}
	// 文章模块热搜表
	private Long id;

	// 目标id
	private Long targetId;

	// redis中存储的score
	private Long score;

	// 1-有效  2-无效
//	private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTargetId() {
		return this.targetId;
	}
    public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	
	public Long getScore() {
		return this.score;
	}
    public void setScore(Long score) {
		this.score = score;
	}
}