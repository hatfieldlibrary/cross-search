package edu.willamette.crossearch.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.willamette.crossearch.model.dspace.SolrResult;
import edu.willamette.crossearch.repository.Domains;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class DspaceDao {

    Logger log = LogManager.getLogger(DspaceDao.class);

    private final String dspaceHost;
    private final String query;
    private final String rootPath;

    @Value("${record.count}")
    String setSize;

    public DspaceDao() {

        dspaceHost = Domains.DSPACE.getHost();
        query = Domains.DSPACE.getQuery();
        rootPath = Domains.DSPACE.getRootPath();
    }

    @Cacheable("dspace")
    public SolrResult execQuery(String terms, String offset, String mode, String collections) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if(mode.contentEquals("phrase")) {
            terms = "\"" + terms + "\"";
        }
        String queryUrl = formatQuery(terms, offset, mode, collections);
        DataRequest dataRequest = new DataRequest();
        StringBuffer buffer =  dataRequest.getData(queryUrl);
        SolrResult dspace;
        if (buffer != null) {
            dspace = gson.fromJson(buffer.toString(), SolrResult.class);
            return dspace;
        }
        return new SolrResult();
    }

    /**
     * Formats url for exist-db api queries.  Supported modes are 'all', 'any', and 'phrase'.
     * @param terms the terms to search
     * @param offset the offset value for the search (1-based offsets)
     * @param mode the query mode
     * @return the url for an exist-db query
     */
    private String formatQuery(String terms, String offset, String mode, String requestCollections) {

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
