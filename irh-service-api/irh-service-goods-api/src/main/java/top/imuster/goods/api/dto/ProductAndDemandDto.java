package top.imuster.goods.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;

import java.util.Date;

/**
 * @ClassName: ProductAndDemandDto
 * @Description: ProductAndDemandDto  商品和需求的共同实体类
 * @author: hmr
 * @date: 2020/5/12 19:02
 */
public class ProductAndDemandDto {

    private Long id;

    private Integer type;

    private String mainPic;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Long userId;

    private Integer browseTimes;

    private String tagNames;

    private String salePrice;

    private String content;

    private Integer collectTotal;

    private Date collectDate;

    public ProductAndDemandDto(ProductInfo productInfo, Date createTime){
        this.id = productInfo.getId();
        this.browseTimes = productInfo.getBrowserTimes().intValue();
        this.type = 1;
        this.createTime = productInfo.getCreateTime();
        this.mainPic = productInfo.getMainPicUrl();
        this.title = productInfo.getTitle();
        this.tagNames = productInfo.getTagNames();
        this.salePrice = productInfo.getSalePrice();
        this.collectTotal = productInfo.getCollectTotal();
        this.collectDate = createTime;
    }

    public ProductAndDemandDto(ProductDemandInfo productDemandInfo, Date createTime){
        this.id = productDemandInfo.getId();
        this.userId = productDemandInfo.getConsumerId();
        this.tagNames = productDemandInfo.getTagNames();
        this.title = productDemandInfo.getTopic();
        this.createTime = productDemandInfo.getCreateTime();
        this.mainPic = productDemandInfo.getMainPic();
        this.content = productDemandInfo.getContent();
        this.browseTimes = productDemandInfo.getBrowserTimes().intValue();
        this.type = 2;
        this.collectDate = createTime;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public Integer getCollectTotal() {
        return collectTotal;
    }

    public void setCollectTotal(Integer collectTotal) {
        this.collectTotal = collectTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(Integer browseTimes) {
        this.browseTimes = browseTimes;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }
}
