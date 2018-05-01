package edu.willamette.crossearch.model.contentdm;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CdmResult {

    @Expose
    Pager pager;
    @Expose
    List<Record> records;

    public CdmResult() {}

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
