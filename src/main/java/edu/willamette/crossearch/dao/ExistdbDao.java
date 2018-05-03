package edu.willamette.crossearch.dao;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.willamette.crossearch.model.existdb.CombinedResult;
import edu.willamette.crossearch.repository.Domains;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ExistdbDao {

    Logger log = LogManager.getLogger(ExistdbDao.class);

    private final String existHost;
    private final String query;
    private final String rootPath;

    @Value("${exist.default}")
    String collections;

    @Value("${record.count}")
    String setSize;

    public ExistdbDao() {

        existHost = Domains.EXIST.getHost();
        query = Domains.EXIST.getQuery();
        rootPath = Domains.EXIST.getRootPath();
    }

    @Cacheable("existdb")
    public CombinedResult execQuery(String terms, String offset, String mode, String collections) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String queryUrl = formatQuery(terms, offset, mode, collections);
        DataRequest dataRequest = new DataRequest();
        StringBuffer buffer =  dataRequest.getData(queryUrl);
        log.debug(queryUrl);
        CombinedResult existResult = gson.fromJson(buffer.toString(), CombinedResult.class);
        return existResult;
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
        if (!requestCollections.contentEquals("all")) {
            collections = requestCollections;
        }

        String url = "http://" +
                existHost + "/" +
                rootPath + "&collection=" +
                collections + "&q=" +
                query + "&records=" +
                setSize + "&start=" +
                offset;

        terms = terms.replace(" ", "+");
        return url.replace("{$query}", terms).replace("{$mode}", mode);
    }
}
