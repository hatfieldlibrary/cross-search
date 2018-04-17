package edu.willamette.crossearch.model.contentdm;

public class Pager {


    private String start;
    private String maxrecs;
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
