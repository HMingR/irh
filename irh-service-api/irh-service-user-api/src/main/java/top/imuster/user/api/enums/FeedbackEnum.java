package top.imuster.user.api.enums;

/**
 * @ClassName: FeedbackEnum
 * @Description: 会员反馈枚举
 * @author: hmr
 * @date: 2020/1/16 19:37
 */
public enum FeedbackEnum {

    PRODUCT(1, "商品"),
    PRODUCT_MESSAGE(2, "商品留言"),
    PRODUCT_EVALUATE(3, "商品评价"),
    ESSAY(4, "帖子");

    Integer type;
    String typeName;

    FeedbackEnum(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static String getNameByType(Integer id){
        FeedbackEnum[] values = FeedbackEnum.values();
        for (FeedbackEnum value : values) {
            if(value.getType() == id){
                return value.getTypeName();
            }
        }
        return "信息";
    }

}
