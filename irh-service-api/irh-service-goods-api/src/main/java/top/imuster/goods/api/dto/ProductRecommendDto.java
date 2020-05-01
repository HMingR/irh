package top.imuster.goods.api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.List;


/**
 * @ClassName: ProductRecommendDto
 * @Description:
 * @author: hmr
 * @date: 2020/5/1 16:07
 */
@Document(collection = "UserRecs")
public class ProductRecommendDto {

    class MongoProductInfo{
        private Integer productId;
        private Double score;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }
    }

    @Id
    private String id;

    private Integer userId;

    private List<MongoProductInfo> recs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<MongoProductInfo> getRecs() {
        return recs;
    }

    public void setRecs(List<MongoProductInfo> recs) {
        this.recs = recs;
    }

}
