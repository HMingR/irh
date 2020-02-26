package top.imuster.user.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: UserTrendDto
 * @Description: 封装给前端的用户增长趋势图
 * @author: hmr
 * @date: 2020/2/26 10:11
 */
public class UserTrendDto implements Serializable {

    private static final long serialVersionUID = 8508923437639826197L;

    private String unit; //单位

    private List<String> abscissaUnit; //横坐标单位

    private List<Long> increments;

    private List<Long> userTotals;

    //纵坐标最大值
    private Long max;

    //纵坐标间隔
    private Integer interval;

    public UserTrendDto() {
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getAbscissaUnit() {
        return abscissaUnit;
    }

    public void setAbscissaUnit(List<String> abscissaUnit) {
        this.abscissaUnit = abscissaUnit;
    }

    public List<Long> getIncrements() {
        return increments;
    }

    public void setIncrements(List<Long> increments) {
        this.increments = increments;
    }

    public List<Long> getUserTotals() {
        return userTotals;
    }

    public void setUserTotals(List<Long> userTotals) {
        this.userTotals = userTotals;
    }
}
