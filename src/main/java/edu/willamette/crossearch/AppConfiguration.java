package edu.willamette.crossearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.willamette.crossearch.dao.DataRequest;
import edu.willamette.crossearch.model.dsCollections.SingleCollection;
import edu.willamette.crossearch.repository.DspaceCollectionMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class AppConfiguration {

    Logger log = LogManager.getLogger(AppConfiguration.class);

    /**
     * Retrieves DSpace collection list and populates hash.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void getDspaceCollections() {

        String restRequest = "http://dspace.willamette.edu:8080/rest/collections";
        DspaceCollectionMap dspaceCollectionMap = DspaceCollectionMap.getInstance();
        DataRequest dataRequest = new DataRequest();
        StringBuffer buffer = dataRequest.getData(restRequest);
        // log.debug(buffer.toString());
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        SingleCollection[] dspace = gson.fromJson(buffer.toString(), SingleCollection[].class);
        for(SingleCollection collection : dspace) {
            dspaceCollectionMap.addCollection(collection.getUuid(), collection.getName());
        }
    }
}
