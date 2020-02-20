package top.imuster.life.api.dto;

import java.io.Serializable;

/**
 * @ClassName: UpCountDto
 * @Description: UpCountDto
 * @author: hmr
 * @date: 2020/2/8 19:49
 */
public class UpCountDto implements Serializable {
    private static final long serialVersionUID = -5448304374292380001L;

    private Long targetId;

    //1-文章  2-评论
    private Integer type;
    private Long count;

    public UpCountDto() {
    }

    public UpCountDto(String key, Long count) {
        String[] split = key.split("::");
        this.targetId = Long.parseLong(split[0]);
        this.type = Integer.parseInt(split[1]);
        this.count = count;
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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
