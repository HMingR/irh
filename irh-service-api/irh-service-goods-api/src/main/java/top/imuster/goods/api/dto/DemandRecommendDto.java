package top.imuster.goods.api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @ClassName: DemandRecommendDto
 * @Description: DemandRecommendDto  需求推荐
 * @author: hmr
 * @date: 2020/5/24 15:31
 */
public class DemandRecommendDto {

    @Id
    private String id;

    @Field("user_id")
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
