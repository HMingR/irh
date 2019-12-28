package top.imuster.goods.api.dto;

import io.swagger.annotations.ApiModel;
import top.imuster.common.base.domain.Page;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: ProductInfoDto
 * @Description:
 * @author: hmr
 * @date: 2019/12/27 12:24
 */
@ApiModel("商品和page的组合实体类")
public class ProductInfoDto {
    private Page<ProductInfo> page;
    private ProductInfo productInfo;

    @Override
    public String toString() {
        return "ProductInfoDto{" +
                "page=" + page +
                ", productInfo=" + productInfo +
                '}';
    }


    public Page<ProductInfo> getPage() {
        return page;
    }

    public void setPage(Page<ProductInfo> page) {
        this.page = page;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
}
