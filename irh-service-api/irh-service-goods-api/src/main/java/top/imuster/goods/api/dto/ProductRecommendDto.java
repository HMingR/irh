package top.imuster.goods.api.dto;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @ClassName: ProductRecommendDto
 * @Description: ProductRecommendDto
 * @author: hmr
 * @date: 2020/5/1 14:08
 */
@Document(collection = "UserRecs")
public class ProductRecommendDto {

    private Long userId;

    private List<String> recs;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getRecs() {
        return recs;
    }

    public void setRecs(List<String> recs) {
        this.recs = recs;
    }
}
