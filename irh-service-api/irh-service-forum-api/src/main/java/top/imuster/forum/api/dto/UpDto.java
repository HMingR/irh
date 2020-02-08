package top.imuster.forum.api.dto;

import top.imuster.forum.api.enums.UpStateEnum;

import java.io.Serializable;

/**
 * @ClassName: UpDto
 * @Description: UpDto
 * @author: hmr
 * @date: 2020/2/8 17:42
 */
public class UpDto implements Serializable {
    private static final long serialVersionUID = 1143404769078891830L;

    private Long targetId;
    private Integer type;
    private Long userId;
    private Integer state = UpStateEnum.NONE.getType();

    public UpDto() {
    }

    public UpDto(Long targetId, Integer type, Long userId, Integer state) {
        this.targetId = targetId;
        this.type = type;
        this.userId = userId;
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
