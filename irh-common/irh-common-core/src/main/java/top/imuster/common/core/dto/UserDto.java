package top.imuster.common.core.dto;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.enums.UserType;

/**
 * @ClassName: UserDto
 * @Description: 所有用户的公共属性的实体类
 * @author: hmr
 * @date: 2019/12/15 15:13
 */
public class UserDto extends BaseDomain {
    private static final long serialVersionUID = 8741880110225481101L;

    //用户的主键id
    private Long userId;

    //用户名
    private String nickname;

    //用户头像地址
    private String pic;

    //登录名--一般为email
    private String loginName;

    //用户类型
    private UserType userType;

    private String academyName;

    private String uType;

    //个性签名
    private String signature;

    public UserDto() {
    }

    public UserDto(Long userId, String loginName, String nickname, String pic, Integer userType) {
        this.userId = userId;
        this.nickname = nickname;
        this.pic = pic;
        this.loginName = loginName;
        setUserTypeById(userType);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", pic='" + pic + '\'' +
                ", loginName='" + loginName + '\'' +
                ", userType=" + userType +
                ", academyName='" + academyName + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public UserDto(Long userId){
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public void setUserType(String name){
        setUserTypeByName(name);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    //根据用户type得到用户UserType枚举
    public void setUserTypeById(Integer id){
        UserType userTypeById = UserType.getUserTypeById(id);
        this.userType = userTypeById;
    }

    public UserType setUserTypeByName(String name){
        return UserType.getUserTypeByName(name);
    }
}
