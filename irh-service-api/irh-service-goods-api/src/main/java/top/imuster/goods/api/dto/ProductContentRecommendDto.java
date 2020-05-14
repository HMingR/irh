package top.imuster.goods.api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @ClassName: ProductContentRecommendDto
 * @Description: ProductContentRecommendDto  基于商品内容的推荐
 * @author: hmr
 * @date: 2020/5/14 9:49
 */
@Document(collection = "rm_product_recs_content_based")
public class ProductContentRecommendDto {

    @Id
    private String id;

    @Field("product_id")
    private Long productId;

    private List<MongoProductInfo> recs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<MongoProductInfo> getRecs() {
        return recs;
    }

    public void setRecs(List<MongoProductInfo> recs) {
        this.recs = recs;
    }
}
