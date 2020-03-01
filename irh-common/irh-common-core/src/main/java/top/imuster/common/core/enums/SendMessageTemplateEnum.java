package top.imuster.common.core.enums;

/**
 * @ClassName: SendMessageTemplateEnum
 * @Description: 发送邮件的模板
 * @author: hmr
 * @date: 2020/3/1 15:24
 */
public enum  SendMessageTemplateEnum {

    USER_REGISTER("用户注册-发送验证码", "email/UserRegister.ftl");


    private String desc;

    private String templateLocation;

    SendMessageTemplateEnum(String desc, String templateLocation) {
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
