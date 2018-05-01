package edu.willamette.crossearch.model.dspace;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SolrResult {

    @Expose
    public Integer count;
    @Expose
    public Integer offset;
    @Expose
    public List<Result> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
