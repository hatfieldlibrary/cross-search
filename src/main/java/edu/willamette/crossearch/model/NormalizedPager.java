package edu.willamette.crossearch.model;

public class NormalizedPager {

    private String startIndex;
    private String pagingIncrement;
    private Integer totalRecs;

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getPagingIncrement() {
        return pagingIncrement;
    }

    public void setPagingIncrement(String pagingIncrement) {
        this.pagingIncrement = pagingIncrement;
    }

    public Integer getTotalRecs() {
        return totalRecs;
    }

    public void setTotalRecs(Integer totalRecs) {
        this.totalRecs = totalRecs;
    }


}
