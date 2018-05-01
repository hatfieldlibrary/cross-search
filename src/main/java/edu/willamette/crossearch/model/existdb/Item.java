package edu.willamette.crossearch.model.existdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import edu.willamette.crossearch.dao.HitsArrayAdapter;

import java.util.List;

public class Item
{
    @Expose
    private String date;

    @Expose
    private String collection;

    @Expose
    private String title;

    @Expose
    private String objid;

    @Expose
    private String display_date;

    @JsonAdapter(HitsArrayAdapter.class)
    @Expose
    private List<Hits> hits;

    private Summaries summaries;

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setCollection(String collection){
        this.collection = collection;
    }
    public String getCollection(){
        return this.collection;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setObjid(String objid){
        this.objid = objid;
    }
    public String getObjid(){
        return this.objid;
    }
    public void setDisplay_date(String display_date){
    this.display_date = display_date;
}
    public String getDisplay_date(){
    return this.display_date;
}
    public void setHits(List<Hits> hits){
        this.hits = hits;
    }
    public List<Hits> getHits(){
        return this.hits;
    }
    public void setSummaries(Summaries summaries){
        this.summaries = summaries;
    }
    public Summaries getSummaries(){
        return this.summaries;
    }
}
