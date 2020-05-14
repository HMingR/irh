package top.imuster.goods.api.dto;

/**
 * @ClassName: GoodsForwardDto
 * @Description: GoodsForwardDto
 * @author: hmr
 * @date: 2020/5/9 9:19
 */
//todo 报错
public class GoodsForwardDto {

    private Long targetId;

    private Integer times;

    public GoodsForwardDto() {
    }

    public GoodsForwardDto(Long targetId, Integer times) {
        this.targetId = targetId;
        this.times = times;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
