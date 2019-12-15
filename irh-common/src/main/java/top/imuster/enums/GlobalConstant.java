package top.imuster.enums;

/**
 * @ClassName: GlobalConstant
 * @Description: 用来记录一些在很多类中都需要共同使用的常量
 * @author: hmr
 * @date: 2019/12/15 16:23
 */
public enum GlobalConstant {
    USER_TOKEN_DTO("userTokenDto");  //在本地线程map中的key

    private String value;

    GlobalConstant(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }}
