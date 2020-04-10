package top.imuster.life.api.dto;

import java.io.Serializable;

/**
 * @ClassName: ArticleRankDto
 * @Description: 论坛模块的用户排名
 * @author: hmr
 * @date: 2020/4/10 9:37
 */
public class ArticleRankDto implements Serializable {
    private static final long serialVersionUID = -2406454397608858598L;
    private Long userId;

    private Long rank;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}
