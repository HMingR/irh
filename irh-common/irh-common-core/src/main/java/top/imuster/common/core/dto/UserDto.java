package top.imuster.common.core.dto;


import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.enums.UserType;

import java.util.Date;

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

    //登录名
    private String loginName;

    //用户类型
    private UserType userType;

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

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
