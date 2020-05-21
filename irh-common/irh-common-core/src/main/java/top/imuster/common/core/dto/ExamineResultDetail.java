package top.imuster.common.core.dto;

import java.util.List;

/**
 * @ClassName: ContentDetail
 * @Description: TODO
 * @author: hmr
 * @date: 2020/5/21 15:49
 */
public class ExamineResultDetail {

    private List<String> politics;

    private List<String> contraband;

    private String flood;

    private List<String> abuse;

    private List<String> porn;

    public List<String> getPolitics() {
        return politics;
    }

    public void setPolitics(List<String> politics) {
        this.politics = politics;
    }

    public List<String> getContraband() {
        return contraband;
    }

    public void setContraband(List<String> contraband) {
        this.contraband = contraband;
    }

    public String getFlood() {
        return flood;
    }

    public void setFlood(String flood) {
        this.flood = flood;
    }

    public List<String> getAbuse() {
        return abuse;
    }

    public void setAbuse(List<String> abuse) {
        this.abuse = abuse;
    }

    public List<String> getPorn() {
        return porn;
    }

    public void setPorn(List<String> porn) {
        this.porn = porn;
    }
}
