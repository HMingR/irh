package top.imuster.common.core.dto;


import top.imuster.common.base.domain.BaseDomain;

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

    private String groupName;

    //用户的类型 10:普通用户  20:卖家  30:社团组织  40: 管理员
    private Integer groupType;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getUserId() {
        return userId;
    }

    public UserDto() {
    }

    public UserDto(Long userId, String loginName, String groupName, Integer groupType, Date loginTime) {
        this.userId = userId;
        this.loginName = loginName;
        this.groupName = groupName;
        this.groupType = groupType;
        this.setCreateTime(loginTime);
    }

    public UserDto(Long userId, String loginName, String groupName, Integer groupType) {
        this.userId = userId;
        this.loginName = loginName;
        this.groupName = groupName;
        this.groupType = groupType;
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

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

}
