package top.imuster.common.core.dto;

/**
 * @ClassName: BrowseRecordDto
 * @Description: 浏览历史
 * @author: hmr
 * @date: 2020/3/28 9:56
 */
public class BrowseRecordDto {

    private String createTime;

    private Long targetId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
