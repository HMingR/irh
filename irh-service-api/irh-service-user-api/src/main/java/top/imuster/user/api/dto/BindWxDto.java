package top.imuster.user.api.dto;

/**
 * @ClassName: BindWxDto
 * @Description: 绑定微信需要的参数
 * @author: hmr
 * @date: 2020/5/19 18:14
 */
public class BindWxDto {

    private String code;

    private String bindEmailCode;

    private Long userId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBindEmailCode() {
        return bindEmailCode;
    }

    public void setBindEmailCode(String bindEmailCode) {
        this.bindEmailCode = bindEmailCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
