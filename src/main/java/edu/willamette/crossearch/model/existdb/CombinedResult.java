package edu.willamette.crossearch.model.existdb;

import com.google.gson.annotations.Expose;

import java.util.List;
public class CombinedResult
{
    @Expose
    private String collectionsSearched;

    @Expose
    private List<CollectionResults> collectionResults;

    public void setCollectionsSearched(String collectionsSearched){
        this.collectionsSearched = collectionsSearched;
    }
    public String getCollectionsSearched(){
        return this.collectionsSearched;
    }
    public void setCollectionResults(List<CollectionResults> collectionResults){
        this.collectionResults = collectionResults;
    }
    public List<CollectionResults> getCollectionResults(){
        return this.collectionResults;
    }
}