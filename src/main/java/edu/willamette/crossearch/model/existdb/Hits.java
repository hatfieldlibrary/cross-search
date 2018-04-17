package edu.willamette.crossearch.model.existdb;

import com.google.gson.annotations.Expose;

public class Hits {

    @Expose
    private String onPage;
    @Expose
    private String page;

    public String getOnPage() {
        return onPage;
    }

    public void setOnPage(String onPage) {
        this.onPage = onPage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
