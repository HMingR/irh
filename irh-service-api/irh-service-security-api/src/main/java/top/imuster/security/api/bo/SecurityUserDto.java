package top.imuster.security.api.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.common.core.dto.UserDto;

@ApiModel("用户登录成功返回的对象")
public class SecurityUserDto extends UserDto {

    //用户昵称
    @ApiModelProperty("用户昵称")
    private String nickname;

    //头像地址
    @ApiModelProperty("头像地址")
    private String portrait;

    private AuthToken authToken;

    public SecurityUserDto(AuthToken authToken){
        this.authToken = authToken;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
