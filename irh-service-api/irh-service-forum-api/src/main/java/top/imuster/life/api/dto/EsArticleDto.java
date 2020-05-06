package top.imuster.life.api.dto;

import org.springframework.data.elasticsearch.annotations.Document;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.life.api.pojo.ArticleInfo;

/**
 * @ClassName: EsArticleDto
 * @Description: EsArticleDto
 * @author: hmr
 * @date: 2020/5/6 11:05
 */
@Document(indexName = "article", type = "article")
public class EsArticleDto extends BaseDomain {

    private Long id;

    private String title;

    private String mainPicture;

    private Long consumerId;

    private String tagNames;

    private String articleSummary;

    public EsArticleDto(ArticleInfo info){
        this.id = info.getId();
        this.title = info.getTitle();
        this.mainPicture = info.getMainPicture();
        this.consumerId = info.getUserId();
        this.tagNames = info.getTagNames();
        this.articleSummary = info.getArticleSummary();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }
}
