package top.imuster.user.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;

/**
 * @ClassName: CheckTypeEnum
 * @Description: 校验用户信息的枚举类
 * @author: hmr
 * @date: 2020/1/14 13:33
 */
@ApiModel("校验用户信息的枚举类")
public enum CheckTypeEnum {

    EMAIL("email","邮箱"),
    QQ("qq", "qq账号"),
    PHONE("phoneNum", "手机号"),
    NICKNAME("nickname", "会员名"),
    ALIPAY("alipayNum", "支付宝账号");

    private String type;
    private String typeName;

    CheckTypeEnum(String type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @Author hmr
     * @Description 通过type获得枚举
     * @Date: 2020/1/14 13:44
     * @param type
     * @reture: top.imuster.user.api.enums.CheckTypeEnum
     **/
    @JsonCreator
    public static CheckTypeEnum getEnumByType(String type){
        for(CheckTypeEnum e : CheckTypeEnum.values()){
            if(e.getType().equals(type)){
                return e;
            }
        }
        return null;
    }

    /**
     * @Author hmr
     * @Description 根据type获得name
     * @Date: 2020/1/14 14:12
     * @param type
     * @reture: java.lang.String
     **/
    public static String getNameByType(String type){
        for(CheckTypeEnum e : CheckTypeEnum.values()){
            if(e.getType().equals(type)){
                return e.getTypeName();
            }
        }
        return null;
    }


}
