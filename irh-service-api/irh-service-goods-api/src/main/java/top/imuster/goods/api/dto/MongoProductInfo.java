package top.imuster.goods.api.dto;

/**
 * @ClassName: MongoProductInfo
 * @Description: MongoProductInfo
 * @author: hmr
 * @date: 2020/5/11 20:51
 */
public class MongoProductInfo {
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
