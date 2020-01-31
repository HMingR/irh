package top.imuster.message.dto;

import java.io.Serializable;

/**
 * @ClassName: GoodsSearchParam
 * @Description: GoodsSearchParam
 * @author: hmr
 * @date: 2020/1/31 10:37
 */
public class ForumSearchParam implements Serializable {
    private static final long serialVersionUID = 8901015039921851580L;
    //关键字
    String keyword;

    //一级分类
    String mt;

    //二级分类
    String st;

    //排序字段
    String sort;

    //过虑字段
    String filter;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
