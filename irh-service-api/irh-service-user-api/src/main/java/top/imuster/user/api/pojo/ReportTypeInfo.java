package top.imuster.user.api.pojo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-05-25 10:03:10
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
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
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}