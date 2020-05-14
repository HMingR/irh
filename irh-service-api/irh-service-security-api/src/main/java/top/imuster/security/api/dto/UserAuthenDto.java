package top.imuster.security.api.dto;

import top.imuster.common.core.validate.ValidateGroup;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName: UserAuthenDto
 * @Description: UserAuthenDto
 * @author: hmr
 * @date: 2020/5/14 12:17
 */
public class UserAuthenDto {

    @NotEmpty(groups = ValidateGroup.addGroup.class, message = "文件路径不能为空")
    private String fileUri;

    @NotEmpty(groups = ValidateGroup.addGroup.class, message = "姓名不能为空")
    private String inputName;

    @NotEmpty(groups = ValidateGroup.addGroup.class, message = "证件号不能为空")
    private String inputCardNo;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getInputCardNo() {
        return inputCardNo;
    }

    public void setInputCardNo(String inputCardNo) {
        this.inputCardNo = inputCardNo;
    }
}
