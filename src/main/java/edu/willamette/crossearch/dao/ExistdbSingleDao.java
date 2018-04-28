package edu.willamette.crossearch.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.willamette.crossearch.model.existdb.Result;
import edu.willamette.crossearch.repository.Domains;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ExistdbSingleDao {
    private final String existHost;
    private final String query;
    private final String rootPath;
    private final String setSize;
    private String collection;

    public ExistdbSingleDao() {

        existHost = Domains.EXIST.getHost();
        query = Domains.EXIST.getQuery();
        rootPath = Domains.EXIST.getRootPath();
        setSize = Domains.EXIST.getSetSize();
    }

    public Result execQuery(String terms, String offset, String mode, String collection) {

        this.collection = collection;
        String queryUrl = formatQuery(terms, offset, mode, collection);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        URL url = null;
        try {
            url = new URL(queryUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DataRequest httpConnection = new DataRequest();
        StringBuffer response = httpConnection.request(url);
        Result existResult = gson.fromJson(response.toString(), Result.class);
        return existResult;

    }

    /**
     * Formats url for exist-db api queries.  Supported modes are 'all', 'any', and 'phrase'.
     *
     * @param terms  the terms to search
     * @param offset the offset value for the search (1-based offsets)
     * @param mode   the query mode
     * @return the url for an exist-db query
     */
    private String formatQuery(String terms, String offset, String mode, String requestCollections) {

        // If specific collections are provided in the request,
        // use them and not the default collection value.
        if (!requestCollections.contentEquals("all")) {
            collection = requestCollections;
        }


        String url = "http://" +
                existHost + "/" +
                rootPath + "&collection=" +
                collection + "&q=" +
                query + "&records=" +
                setSize + "&start=" +
                offset;

        terms = terms.replace(" ", "+");
        return url.replace("{$query}", terms).replace("{$mode}", mode);
    }

}