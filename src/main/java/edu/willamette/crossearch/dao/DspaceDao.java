package edu.willamette.crossearch.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.willamette.crossearch.model.contentdm.Result;
import edu.willamette.crossearch.model.dspace.SolrResult;
import edu.willamette.crossearch.model.existdb.CombinedResult;
import edu.willamette.crossearch.repository.Domains;
import edu.willamette.crossearch.repository.DspaceResponse;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class DspaceDao {

    private final String dspaceHost;
    private final String query;
    private final String rootPath;
    private final String setSize;

    public DspaceDao() {

        dspaceHost = Domains.DSPACE.getHost();
        query = Domains.DSPACE.getQuery();
        rootPath = Domains.DSPACE.getRootPath();
        setSize = Domains.DSPACE.getSetSize();
    }
    public SolrResult execQuery(String terms, String offset, String mode, String collections) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String queryUrl = formatQuery(terms, offset, mode, collections);
        DataRequest dataRequest = new DataRequest();
        StringBuffer buffer =  dataRequest.getData(queryUrl);
        SolrResult dspace = gson.fromJson(buffer.toString(), SolrResult.class);
        return dspace;
    }

    /**
     * Formats url for exist-db api queries.  Supported modes are 'all', 'any', and 'phrase'.
     * @param terms the terms to search
     * @param offset the offset value for the search (1-based offsets)
     * @param mode the query mode
     * @return the url for an exist-db query
     */
    private String formatQuery(String terms, String offset, String mode, String requestCollections) {

        // If specific collections are provided in the request,
        // use them and not the default collection value.

        // Ignore this for now. It may be useful for single collection searches (not yet implemented in dspace api)
        //if (!requestCollections.contentEquals("all")) {
        //    collections = requestCollections;
       // }


        String url = "http://" +
                dspaceHost + "/" +
                rootPath + "/" +
                query + "/" +
                offset + "/" +
                setSize;

        terms = terms.replace(" ", "+");
        return url.replace("{$query}", terms);
    }

}
