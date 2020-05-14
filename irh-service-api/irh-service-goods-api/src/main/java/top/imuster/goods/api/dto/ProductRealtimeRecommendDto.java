package top.imuster.goods.api.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @ClassName: ProductRealtimeRecommendDto
 * @Description: 用户的实时推荐
 * @author: hmr
 * @date: 2020/5/14 15:59
 */
@Document(collection = "rm_product_stream_recs")
public class ProductRealtimeRecommendDto {

    @Field("user_id")
    private Long userId;

    private List<MongoProductInfo> recs;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<MongoProductInfo> getRecs() {
        return recs;
    }

    public void setRecs(List<MongoProductInfo> recs) {
        this.recs = recs;
    }
}
