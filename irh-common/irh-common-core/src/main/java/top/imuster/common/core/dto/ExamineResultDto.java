package top.imuster.common.core.dto;

/**
 * @ClassName: ContentDto
 * @Description: ContentDto
 * @author: hmr
 * @date: 2020/5/21 15:44
 */
public class ExamineResultDto {

    private String suggestion;

    private ExamineResultDetail detail;

    @Override
    public String toString() {
        return "ContentDto{" +
                "suggestion='" + suggestion + '\'' +
                ", detail=" + detail +
                '}';
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public ExamineResultDetail getDetail() {
        return detail;
    }

    public void setDetail(ExamineResultDetail detail) {
        this.detail = detail;
    }
}
