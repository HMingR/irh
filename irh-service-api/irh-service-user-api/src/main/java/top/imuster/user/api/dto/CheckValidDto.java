package top.imuster.user.api.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName: CheckValidDto
 * @Description: 校验参数
 * @author: hmr
 * @date: 2020/1/13 9:37
 */
@ApiModel("校验参数")
public class CheckValidDto implements Serializable {
    private static final long serialVersionUID = -1002692539428990955L;

    //需要校验的值
    @ApiModelProperty("需要校验的值")
    @NotEmpty(message = "校验的值不能为空")
    private String validValue;

    //需要校验的类型
    @NotEmpty(message = "校验类型不能为空")
    @ApiModelProperty("需要校验的类型")
    private String type;

    public String getValidValue() {
        return validValue;
    }

    public void setValidValue(String validValue) {
        this.validValue = validValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
