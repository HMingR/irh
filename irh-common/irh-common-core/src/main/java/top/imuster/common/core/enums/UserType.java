package top.imuster.common.core.enums;

/**
 * @ClassName: UserType
 * @Description: UserType
 * @author: hmr
 * @date: 2020/1/29 14:56
 */
public enum UserType{
    CUSTOMER(10, "普通会员"),
    SELLER(20, "卖家"),
    ORGANIZATION(30, "组织社团"),
    MANAGEMENT(40, "管理员");

    int type;
    String name;

    UserType(Integer type, String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
