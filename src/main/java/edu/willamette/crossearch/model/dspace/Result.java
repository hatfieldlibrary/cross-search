package edu.willamette.crossearch.model.dspace;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Result {

    @Expose
    public String handle;
    @Expose
    public String resourceid;
    @Expose
    public String resourcetype;
    @Expose
    public List<String> author;
    @Expose
    public List<String> title;
    @Expose
    public String defaultTitle;
    @Expose
    public List<String> description;
    @Expose
    public List<String> collectionId;

    public List<String> getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(List<String> collectionId) {
        this.collectionId = collectionId;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getResourceid() {
        return resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcetype() {
        return resourcetype;
    }

    public void setResourcetype(String resourcetype) {
        this.resourcetype = resourcetype;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }
}
