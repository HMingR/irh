package top.imuster.user.api.dto;

/**
 * @ClassName: UserAuthenResultDto
 * @Description: UserAuthenResultDto 用户实名认证结果dto
 * @author: hmr
 * @date: 2020/5/30 17:15
 */
public class UserAuthenResultDto {

    private boolean success;

    private Long userId;

    //真实名字
    private String realName;

    //证件号码
    private String certificateNum;

    //学院名称
    private String academyName;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }
}
