package top.imuster.life.api.enums;

/**
 * @ClassName: UpStateEnum
 * @Description: UpStateEnum
 * @author: hmr
 * @date: 2020/2/8 17:52
 */
public enum  UpStateEnum {
    UP(1, "点赞"),
    NONE(2, "取消点赞/未点赞");

    private Integer type;
    private String desc;

    UpStateEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
