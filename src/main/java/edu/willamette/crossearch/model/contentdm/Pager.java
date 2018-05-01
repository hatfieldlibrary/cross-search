package edu.willamette.crossearch.model.contentdm;

import com.google.gson.annotations.Expose;

public class Pager {

    @Expose
    private String start;
    @Expose
    private String maxrecs;
    @Expose
    private Integer total;

    public Pager() {}

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getMaxrecs() {
        return maxrecs;
    }

    public void setMaxrecs(String maxrecs) {
        this.maxrecs = maxrecs;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
