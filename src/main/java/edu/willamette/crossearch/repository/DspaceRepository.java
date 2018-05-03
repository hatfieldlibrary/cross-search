package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.dao.DspaceDao;
import edu.willamette.crossearch.model.NormalizedPager;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.model.dspace.Result;
import edu.willamette.crossearch.model.dspace.SolrResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DspaceRepository implements RepositoryInterface {

    Logger log = LogManager.getLogger(DspaceRepository.class);

    @Autowired
    DspaceDao dspaceDao;

    @Value("${record.count}")
    String setSize;

    @Override
    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        log.debug("Dspace query");
        SolrResult dspaceResult = dspaceDao.execQuery(terms, offset, mode, collections);
        return normalize(dspaceResult, offset);
    }

    private NormalizedResult normalize(SolrResult results, String offset) {

        DspaceCollectionMap collectionMap = DspaceCollectionMap.getInstance();

        NormalizedResult normalizedResult = new NormalizedResult();
        List<NormalizedRecord> mappedResult = new ArrayList<>();
        try {
            for (Result record : results.getResults()) {
                log.debug(record.getCollectionId().size());
                NormalizedRecord normalizedRecord = new NormalizedRecord();
                normalizedRecord.setRepository("dspace");
                normalizedRecord.setCollection(record.getCollectionId().get(0));
                normalizedRecord.setDate("");
                if (record.getDescription() != null && record.getDescription().size() > 0) {
                    normalizedRecord.setDescription(record.getDescription().get(0));
                }
                normalizedRecord.setId(record.getResourceid());
                normalizedRecord.setFiletype(record.getResourcetype());
                normalizedRecord.setLocator(record.getHandle());
                // Until we have a way to retrieve all dspace collection names, just adding a stub value for missing
                // information.
                String name = collectionMap.getCollectionName(record.getCollectionId().get(0));
                if (name == null) {
                    name = "Unknown DSpace Collection";
                }
                normalizedRecord.setSource(name);
                normalizedRecord.setTitle(record.getDefaultTitle());
                mappedResult.add(normalizedRecord);
            }

            Map<String, List<NormalizedRecord>> map =
                    mappedResult.stream()
                            .collect(Collectors.groupingBy(NormalizedRecord::getSource));

            normalizedResult.setRecords(map);
            NormalizedPager normalizedPager = new NormalizedPager();
            normalizedPager.setPagingIncrement(setSize);
            normalizedPager.setStartIndex(Integer.toString(results.getOffset()));
            normalizedPager.setTotalRecs(results.getCount());
            normalizedResult.setPager(normalizedPager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return normalizedResult;
    }

}
