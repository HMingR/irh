package top.imuster.user.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-05-25 10:03:10
 */
public class ReportTypeInfo extends BaseDomain {

	private static final long serialVersionUID = 5839335201L;

	public ReportTypeInfo() {
		//默认无参构造方法
	}
	//  举报类型表
	private Long id;

	//  父分类id
	private Long parentId;

	// max length: 255
	private String desc;

	//  1-无效  2-有效
//	private Short state;

	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public Long getParentId() {
		return this.parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getDesc() {
		return this.desc;
	}
    public void setDesc(String desc) {
		this.desc = desc;
	}
}