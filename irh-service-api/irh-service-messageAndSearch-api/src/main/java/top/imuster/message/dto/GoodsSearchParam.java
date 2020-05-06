package top.imuster.message.dto;

/**
 * @ClassName: GoodsSearchParam
 * @Description: GoodsSearchParam
 * @author: hmr
 * @date: 2020/1/31 10:37
 */
public class GoodsSearchParam {

    //查询的关键字
    String keyword;

    //价格区间
    String priceMin;

    String priceMax;

    Integer tradeType;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
}
