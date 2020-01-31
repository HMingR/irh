package top.imuster.message.dto;

/**
 * @ClassName: GoodsSearchParam
 * @Description: GoodsSearchParam
 * @author: hmr
 * @date: 2020/1/31 10:37
 */
public class GoodsSearchParam extends ForumSearchParam {

    //新旧程度
    String oldDegree;

    //价格区间
    Float priceMin;
    Float priceMax;

    public String getOldDegree() {
        return oldDegree;
    }

    public void setOldDegree(String oldDegree) {
        this.oldDegree = oldDegree;
    }

    public Float getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Float priceMin) {
        this.priceMin = priceMin;
    }

    public Float getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Float priceMax) {
        this.priceMax = priceMax;
    }
}
