package edu.willamette.crossearch.model.existdb;

import com.google.gson.annotations.Expose;

public class CollectionResults
{
    @Expose
    private String collectionName;

    @Expose
    private Result result;

    public void setCollectionName(String collectionName){
        this.collectionName = collectionName;
    }
    public String getCollectionName(){
        return this.collectionName;
    }
    public void setResult(Result result){
        this.result = result;
    }
    public Result getResult(){
        return this.result;
    }
}