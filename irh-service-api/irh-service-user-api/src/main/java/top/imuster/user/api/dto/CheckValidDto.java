package top.imuster.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.imuster.user.api.enums.CheckTypeEnum;

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
    private CheckTypeEnum type;

    public void setType(CheckTypeEnum type) {
        this.type = type;
    }

    public CheckTypeEnum getType() {
        return type;
    }

    public String getValidValue() {
        return validValue;
    }

    public void setValidValue(String validValue) {
        this.validValue = validValue;
    }
}
