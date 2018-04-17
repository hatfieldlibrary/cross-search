package edu.willamette.crossearch.model;

import io.reactivex.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NormalizedResult {

    @NonNull
    NormalizedPager pager;
    @NonNull
    List<NormalizedRecord> records;

    @NonNull
    public NormalizedPager getPager() {
        return pager;
    }

    public void setPager( @NonNull NormalizedPager pager) {
        this.pager = pager;
    }

    @NonNull
    public List<NormalizedRecord> getRecords() {
        return records;
    }

    public void setRecords( @NonNull List<NormalizedRecord> records) {
        this.records = records;
    }

}
