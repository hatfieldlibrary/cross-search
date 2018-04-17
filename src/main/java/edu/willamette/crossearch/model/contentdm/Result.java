package edu.willamette.crossearch.model.contentdm;

import java.util.List;

public class Result {

    Pager pager;
    List<Record> records;

    public Result() {}

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> cdmResultList) {
        this.records = cdmResultList;
    }

}
