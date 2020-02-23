package top.imuster.life.api.dto;

import java.io.Serializable;

/**
 * @ClassName: ForwardDto
 * @Description: ForwardDto
 * @author: hmr
 * @date: 2020/2/23 17:23
 */
public class ForwardDto implements Serializable {
    private static final long serialVersionUID = -1586055185735522640L;

    private Long times;
    private Long targetId;

    public ForwardDto() {
    }

    public ForwardDto(Long targetId, Long times) {
        this.times = times;
        this.targetId = targetId;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
