package top.imuster.domain.base;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseDomain extends BaseQuery {
    private Date gmtCreate;
    private Date gmtModify;
    private Integer yn;


    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
