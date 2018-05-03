package edu.willamette.crossearch.model;

import io.reactivex.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

public class NormalizedResultMap {

    @NonNull
    NormalizedPager pager;
    @NonNull
    HashMap<String, List<NormalizedRecord>> records;

    @NonNull
    public NormalizedPager getPager() {
        return pager;
    }

    public void setPager( @NonNull NormalizedPager pager) {
        this.pager = pager;
    }

    @NonNull
    public HashMap<String, List<NormalizedRecord>> getRecords() {
        return records;
    }

    public void setRecords( @NonNull  HashMap<String, List<NormalizedRecord>> records) {
        this.records = records;
    }
}
