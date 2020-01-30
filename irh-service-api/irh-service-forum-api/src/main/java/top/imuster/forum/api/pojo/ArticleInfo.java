package top.imuster.forum.api.pojo;


import top.imuster.common.base.domain.BaseDomain;

/**
 * 
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public class ArticleInfo extends BaseDomain {

	private static final long serialVersionUID = 7095780754L;

	public ArticleInfo() {
		//默认无参构造方法
	}
	// 文章/帖子表主键
	private Long id;

	// 标题, max length: 255
	private String title;

	// 文章/帖子分类id
	private Long articleCategory;

	// 封面图片, max length: 255
	private String mainPicture;

	// 内容
	private String content;

	// 1-无效  2-有效
//	private Short state;



	public Long getId() {
		return this.id;
	}
    public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
    public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getArticleCategory() {
		return this.articleCategory;
	}
    public void setArticleCategory(Long articleCategory) {
		this.articleCategory = articleCategory;
	}
	
	public String getMainPicture() {
		return this.mainPicture;
	}
    public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}
	
	public String getContent() {
		return this.content;
	}
    public void setContent(String content) {
		this.content = content;
	}
	
}