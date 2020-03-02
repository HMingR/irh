package top.imuster.order.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: OrderTrendDto
 * @Description: 订单趋势图
 * @author: hmr
 * @date: 2020/3/2 15:24
 */
public class OrderTrendDto implements Serializable {
    private static final long serialVersionUID = 2234170966641424720L;

    private String unit; //单位

    private List<String> abscissaUnit; //横坐标

    //
    private List<Long> increments;

    //订单数
    private List<Long> orderTotals;  //折线

    //订单总金额趋势
    private List<Long> orderAmount;

    //商品总数
    private Long productTotal;

    //订单总数
    private Long orderTotal;

    //纵坐标最大值
    private Long max;

    //纵坐标间隔
    private Integer interval;

    public List<Long> getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(List<Long> orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<Long> getOrderTotals() {
        return orderTotals;
    }

    public void setOrderTotals(List<Long> orderTotals) {
        this.orderTotals = orderTotals;
    }

    public Long getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Long productTotal) {
        this.productTotal = productTotal;
    }

    public Long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getAbscissaUnit() {
        return abscissaUnit;
    }

    public void setAbscissaUnit(List<String> abscissaUnit) {
        this.abscissaUnit = abscissaUnit;
    }

    public List<Long> getIncrements() {
        return increments;
    }

    public void setIncrements(List<Long> increments) {
        this.increments = increments;
    }



    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
