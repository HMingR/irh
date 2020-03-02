package top.imuster.common.core.enums;

/**
 * @ClassName: TemplateEnum
 * @Description: 发送邮件的模板
 * @author: hmr
 * @date: 2020/3/1 15:24
 */
public enum TemplateEnum {

    USER_REGISTER("用户注册-发送验证码", "email/UserRegister.ftl"),

    //todo 商品详情页还没有
    PRODUCT_TEMPLATE("商品详情页", "");

    private String desc;

    private String templateLocation;

    TemplateEnum(String desc, String templateLocation) {
        this.desc = desc;
        this.templateLocation = templateLocation;
    }

    public String getDesc() {
        return desc;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }
}
