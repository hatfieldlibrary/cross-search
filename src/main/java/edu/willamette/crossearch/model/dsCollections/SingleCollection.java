package edu.willamette.crossearch.model.dsCollections;

import com.google.gson.annotations.Expose;

public class SingleCollection {

    @Expose
    String uuid;
    @Expose
    String name;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
