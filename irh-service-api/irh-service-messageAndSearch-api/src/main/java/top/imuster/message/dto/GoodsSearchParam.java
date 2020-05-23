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

    //价格排序类型  2-升序 1-降序
    Integer priceOrder;

    //创建时间排序  2-升序  1-降序
    Integer timeOrder;

    //新旧程度排序  1-降序  2-升序
    Integer oldDegreeOrder;

    public Integer getOldDegreeOrder() {
        return oldDegreeOrder;
    }

    public void setOldDegreeOrder(Integer oldDegreeOrder) {
        this.oldDegreeOrder = oldDegreeOrder;
    }

    public Integer getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(Integer priceOrder) {
        this.priceOrder = priceOrder;
    }

    public Integer getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(Integer timeOrder) {
        this.timeOrder = timeOrder;
    }

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
