package top.imuster.goods.api.dto;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @ClassName: MongoProductInfo
 * @Description: MongoProductInfo
 * @author: hmr
 * @date: 2020/5/11 20:51
 */
public class MongoProductInfo {

    @Field("product_id")
    private Long productId;
    private Double score;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
