package edu.willamette.crossearch.model.contentdm;

import com.google.gson.annotations.Expose;

public class Record {

    @Expose
    private String collection;
    @Expose
    private Integer pointer;
    @Expose
    private String creato;
    @Expose
    private String filetype;
    @Expose
    private Integer parentobject;
    @Expose
    private String descri;
    @Expose
    private String title;
    @Expose
    private String source;
    @Expose
    private String date;
    @Expose
    private String find;

    public Record() {}

    public String getCreato() {
        return creato;
    }

    public void setCreato(String creato) {
        this.creato = creato;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Integer getPointer() {
        return pointer;
    }

    public void setPointer(Integer pointer) {
        this.pointer = pointer;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public Integer getParentobject() {
        return parentobject;
    }

    public void setParentobject(Integer parentobject) {
        this.parentobject = parentobject;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String bdate) {
        this.date = bdate;
    }

    public String getFind() {
        return find;
    }

    public void setFind(String find) {
        this.find = find;
    }

}
