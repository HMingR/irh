package top.imuster.life.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @ClassName: UserBriefDto
 * @Description: UserBriefDto
 * @author: hmr
 * @date: 2020/2/15 15:29
 */
@ApiModel("用户简略信息")
public class UserBriefDto implements Serializable {

    private static final long serialVersionUID = 4527065561228797468L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String nickname;

    @ApiModelProperty("文章总数")
    private Long articleTotal;

    @ApiModelProperty("获赞总数")
    private Long upTotal;

    @ApiModelProperty("收藏总数")
    private Long collectTotal;

    @ApiModelProperty("文章被浏览的总次数")
    private Long browserTotal;

    @ApiModelProperty("留言总数")
    private Long reviewTotal;


    public Long getReviewTotal() {
        return reviewTotal;
    }

    public void setReviewTotal(Long reviewTotal) {
        this.reviewTotal = reviewTotal;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public Long getBrowserTotal() {
        return browserTotal;
    }

    public void setBrowserTotal(Long browserTotal) {
        this.browserTotal = browserTotal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleTotal() {
        return articleTotal;
    }

    public void setArticleTotal(Long articleTotal) {
        this.articleTotal = articleTotal;
    }

    public Long getUpTotal() {
        return upTotal;
    }

    public void setUpTotal(Long upTotal) {
        this.upTotal = upTotal;
    }

    public Long getCollectTotal() {
        return collectTotal;
    }

    public void setCollectTotal(Long collectTotal) {
        this.collectTotal = collectTotal;
    }
}
