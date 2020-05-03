package top.imuster.goods.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-05-03 15:01:34
 */
public class ProductDemandReplyInfo extends BaseDomain {

	private static final long serialVersionUID = 12569429257L;

	public ProductDemandReplyInfo() {
		//默认无参构造方法
	}
	// 需求回复表
	private Long id;

	//  需求表的主键id
	private Long demandId;

	//  发布消息的人的id
	private Long userId;

	// 一级留言id
	private Long firstClassId;

	// 父回复id
	private Long parentId;

	// 1-新的发布 2-引用商品订单信息
	private Short releaseType;

	//  如果type为2，则标识二手商品表的主键id
	private Long targetId;

	// 留言内容, max length: 255
	private String content;

	//一级留言下的留言个数
	private Integer childTotal;

	//父级留言的作者(回复人的id)
	private Long parentWriterId;

	// 1-删除   2-有效  3-被需求发布者选中
	//private Short state;


	public Long getParentWriterId() {
		return parentWriterId;
	}

	public void setParentWriterId(Long parentWriterId) {
		this.parentWriterId = parentWriterId;
	}

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
	
	public Long getFirstClassId() {
		return this.firstClassId;
	}
    public void setFirstClassId(Long firstClassId) {
		this.firstClassId = firstClassId;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Short getReleaseType() {
		return this.releaseType;
	}
    public void setReleaseType(Short releaseType) {
		this.releaseType = releaseType;
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