package top.imuster.common.core.dto;

import top.imuster.common.core.enums.BrowserType;

/**
 * @ClassName: BrowserTimesDto
 * @Description: BrowserTimesDto
 * @author: hmr
 * @date: 2020/2/15 17:03
 */
public class BrowserTimesDto {

    private Long targetId;
    private Long total;
    private BrowserType type;

    public BrowserTimesDto() {
    }

    public BrowserTimesDto(Long targetId, Long total, BrowserType type) {
        this.targetId = targetId;
        this.total = total;
        this.type = type;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public BrowserType getType() {
        return type;
    }

    public void setType(BrowserType type) {
        this.type = type;
    }
}
