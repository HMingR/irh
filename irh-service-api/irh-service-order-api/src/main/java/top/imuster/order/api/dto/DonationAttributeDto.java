package top.imuster.order.api.dto;

/**
 * @ClassName: DonationAttributeDto
 * @Description: DonationAttributeDto
 * @author: hmr
 * @date: 2020/4/27 14:19
 */
public class DonationAttributeDto {

    private Long targetId;

    private Integer total;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
