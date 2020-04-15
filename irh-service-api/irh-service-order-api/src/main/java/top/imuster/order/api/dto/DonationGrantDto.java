package top.imuster.order.api.dto;

import top.imuster.order.api.pojo.OrderInfo;

import java.util.List;

/**
 * @ClassName: DonationGrantDto
 * @Description: 发放金额
 * @author: hmr
 * @date: 2020/4/14 17:11
 */
public class DonationGrantDto {

    //申请的id
    private Long applyId;

    //选择的
    private List<OrderInfo> orderInfos;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public List<OrderInfo> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(List<OrderInfo> orderInfos) {
        this.orderInfos = orderInfos;
    }
}
