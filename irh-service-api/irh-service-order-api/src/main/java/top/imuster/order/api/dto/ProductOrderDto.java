package top.imuster.order.api.dto;

import io.swagger.annotations.ApiModel;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.order.api.pojo.OrderInfo;

/**
 * @ClassName: ProductOrderDto
 * @Description: 商品订单表dto对象
 * @author: hmr
 * @date: 2019/12/28 12:20
 */
@ApiModel("下单实体")
public class ProductOrderDto {
    private OrderInfo orderInfo;
    private ProductInfo productInfo;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
}
