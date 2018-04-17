package edu.willamette.crossearch.model.contentdm;

public class Record {

    private String collection;
    private String pointer;
    private String filetype;
    private Integer parentobject;
    private String descri;
    private String title;
    private String source;
    private String date;
    private String find;

    public Record() {}

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
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
