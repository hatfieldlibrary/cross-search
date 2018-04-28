package edu.willamette.crossearch.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.willamette.crossearch.model.contentdm.Result;
import edu.willamette.crossearch.repository.Domains;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ContentdmDao {

    Logger log = LogManager.getLogger(ContentdmDao.class);

    private final String cdmHost;
    private final String query;
    private final String sort;
    private final String rootPath;
    private final String returnFields;
    private final String setSize;

    @Value("${cdm.default}")
    String collections;

    public ContentdmDao() {

        cdmHost = Domains.CONDM.getHost();
        query = Domains.CONDM.getQuery();
        sort = Domains.CONDM.getSort();
        rootPath = Domains.CONDM.getRootPath();
        returnFields = Domains.CONDM.getReturnFields();
        setSize = Domains.CONDM.getSetSize();
    }

    public Result execQuery (String terms, String offset, String mode, String collections) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String queryUrl = formatQuery(terms, offset, mode, collections);
        DataRequest dataRequest = new DataRequest();
        StringBuffer buffer =  dataRequest.getData(queryUrl);
        Result cdmResult = gson.fromJson(buffer.toString(), Result.class);
        return cdmResult;
    }

    /**
     * Formats url for contentdm api queries.  Supported modes are 'all', 'any', and 'exact'.
     * The 'exact' contentdm search mode is derived from the generic input mode 'phrase'.
     * @param terms the terms to search
     * @param offset the offset value for the search (0-based offsets)
     * @param mode the query mode
     * @return the url for an exist-db query
     */
    private String formatQuery(String terms, String offset, String mode, String requestCollections) {

        // If specific collections are provided in the request,
        // use them and not the default collection value.
        if (!requestCollections.contentEquals("all")) {
            collections = requestCollections.replace(",", "!");
        }

        String url = "http://" +
                cdmHost + "/" +
                rootPath + "/" +
                collections + "/" +
                query + "/" +
                returnFields + "/" +
                sort + "/" +
                setSize + "/" +
                offset +
                "/1/0/0/0/0/0/json";

        String cdmMode = getCdmMode(mode);
        terms = terms.replace(" ", "+");
        return url.replace("{$query}", terms).replace("{$mode}", cdmMode);
    }

    private String getCdmMode(String mode) {

        if (mode.contentEquals("phrase")) {
            return "exact";
        }
        return mode;
    }

}
