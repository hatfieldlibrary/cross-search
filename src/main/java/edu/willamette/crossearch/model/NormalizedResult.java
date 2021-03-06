package edu.willamette.crossearch.model;

import io.reactivex.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalizedResult {

    @NonNull
    NormalizedPager pager;
    @NonNull
    Map<String, List<NormalizedRecord>> records;

    @NonNull
    public NormalizedPager getPager() {
        return pager;
    }

    public void setPager( @NonNull NormalizedPager pager) {
        this.pager = pager;
    }

    @NonNull
    public Map<String, List<NormalizedRecord>> getRecords() {
        return records;
    }

    public void setRecords( @NonNull  Map<String, List<NormalizedRecord>> records) {
        this.records = records;
    }

}
