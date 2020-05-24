package top.imuster.goods.api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @ClassName: ProductUserTagRecommendDto
 * @Description: 根据用户选择的标签推挤三
 * @author: hmr
 * @date: 2020/5/24 20:08
 */
@Document(collection = "rm_product_user_tagnames")
public class ProductUserTagRecommendDto {

    @Id
    private String id;

    @Field("user_id")
    private Long userId;

    private List<MongoProductInfo> recs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
