package top.imuster.goods.api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: ESProductDto
 * @Description: ESProductDto
 * @author: hmr
 * @date: 2020/5/6 10:31
 */
@Document(indexName = "goods", type = "goods", createIndex = false)
public class ESProductDto extends BaseDomain {

    private Integer type;

    private String title;

    @Id
    private Long id;

    private String tagNames;

    private String mainPicUrl;

    private String salePrice;

    private String desc;

    private Integer tradeType;

    private Long consumerId;

    private Integer oldDegree;

    public ESProductDto() {
    }

    public ESProductDto(ProductInfo info){
        this.type = 1;
        this.id = info.getId();
        this.title = info.getTitle();
        this.tagNames = info.getTagNames();
        this.mainPicUrl = info.getMainPicUrl();
        this.salePrice = info.getSalePrice();
        this.desc = info.getProductDesc();
        this.tradeType = info.getTradeType();
        this.consumerId = info.getConsumerId();
        this.oldDegree = info.getOldDegree();
        this.setCreateTime(DateUtil.current());
    }

    public ESProductDto(ProductDemandInfo info){
        this.type = 2;
        this.id = info.getId();
        this.title = info.getTopic();
        this.desc = info.getContent();
        this.consumerId = info.getConsumerId();
        this.mainPicUrl = info.getMainPic();
        this.tagNames = info.getTagNames();
    }

    public Integer getOldDegree() {
        return oldDegree;
    }

    public void setOldDegree(Integer oldDegree) {
        this.oldDegree = oldDegree;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagNames() {
        return tagNames;
    }

    public void setTagNames(String tagNames) {
        this.tagNames = tagNames;
    }

    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

}
