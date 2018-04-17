package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.existdb.Item;

public class ExistUtils {

    public NormalizedRecord getNormalizedRecord(Item item) {
        NormalizedRecord normalizedRecord = new NormalizedRecord();
        normalizedRecord.setRepository("exist");
        normalizedRecord.setCollection(item.getCollection());
        normalizedRecord.setDate(item.getDisplay_date());
        //  normalizedRecord.setDescription(item.getDescription());
        normalizedRecord.setId(item.getDate());
        normalizedRecord.setHits(item.getHits());
        normalizedRecord.setFiletype("xml");
        normalizedRecord.setLocator(item.getDate());
        normalizedRecord.setSource(getCollectionName(item.getCollection()));
        normalizedRecord.setTitle(item.getTitle());
        return normalizedRecord;
    }

    private String getCollectionName(String coll) {

        switch (coll) {
            case "collegian":
                return "Willamette University Collegian";
            case "wallulah":
                return "Wallulah (Student Yearbook)";
            case "scene":
                return "Alumni Publications";
            case "bulletinscatalogs":
                return "Willamette University Catalogs & Bulletins";
            case "commencement":
                return "Commencement Programs";
            case "puritan":
                return "Willamette University Puritan";
            case "scrapbooks":
                return "Scrapbooks";
            case "handbooks":
                return "Student Handbooks - College of Liberal Arts";
            default:
                return "";
        }
    }
}
