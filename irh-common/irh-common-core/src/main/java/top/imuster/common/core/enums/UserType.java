package top.imuster.common.core.enums;

/**
 * @ClassName: UserType
 * @Description: UserType
 * @author: hmr
 * @date: 2020/1/29 14:56
 */
public enum UserType{
    //10:普通会员 20:服务人员  30:校园组织 40:校园社团 50:管理员
    CUSTOMER(10, "普通会员"),
    SELLER(20, "服务人员"),
    ORGANIZATION(30, "校园组织"),
    ASSOCIATION(40, "校园社团"),
    MANAGEMENT(50, "管理员");

    Integer type;
    String name;

    UserType(Integer type, String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static UserType getUserTypeById(Integer type){
        UserType[] values = UserType.values();
        for (UserType value : values) {
            if(value.type == type){
                return value;
            }
        }
        return null;
    }
}
