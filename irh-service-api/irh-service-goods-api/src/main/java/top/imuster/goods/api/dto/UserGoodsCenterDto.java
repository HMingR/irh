package top.imuster.goods.api.dto;

/**
 * @ClassName: UserGoodsCenterDto
 * @Description: UserGoodsCenterDto 用户商城中心
 * @author: hmr
 * @date: 2020/5/26 16:29
 */
public class UserGoodsCenterDto {

    //访问次数
    private Integer browseTotal;

    //收藏次数
    private Integer collectTotal;

    //卖了多少东西
    private Integer saleTotal;

    //一共多少商品
    private Integer goodsTotal;

    //捐赠金额
    private String donationTotal;

    public Integer getBrowseTotal() {
        return browseTotal;
    }

    public void setBrowseTotal(Integer browseTotal) {
        this.browseTotal = browseTotal;
    }

    public Integer getCollectTotal() {
        return collectTotal;
    }

    public void setCollectTotal(Integer collectTotal) {
        this.collectTotal = collectTotal;
    }

    public Integer getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Integer saleTotal) {
        this.saleTotal = saleTotal;
    }

    public Integer getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(Integer goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public String getDonationTotal() {
        return donationTotal;
    }

    public void setDonationTotal(String donationTotal) {
        this.donationTotal = donationTotal;
    }
}
