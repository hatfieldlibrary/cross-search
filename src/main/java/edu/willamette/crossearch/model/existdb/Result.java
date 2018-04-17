package edu.willamette.crossearch.model.existdb;

import com.google.gson.annotations.Expose;

import java.util.List;
public class Result
{
    @Expose
    private String total;

    @Expose
    private List<Item> item;

    public void setTotal(String total){
        this.total = total;
    }
    public String getTotal(){
        return this.total;
    }
    public void setItem(List<Item> item){
        this.item = item;
    }
    public List<Item> getItem(){
        return this.item;
    }
}
