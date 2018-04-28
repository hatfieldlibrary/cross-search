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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DspaceRepository implements RepositoryInterface {

    Logger log = LogManager.getLogger(DspaceRepository.class);

    @Autowired
    DspaceDao dspaceDao;

    @Override
    @Cacheable("search")
    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        log.debug("Dspace query");
        SolrResult dspaceResult = dspaceDao.execQuery(terms, offset, mode, collections);
        return normalize(dspaceResult, offset);
    }

    private NormalizedResult normalize(SolrResult results, String offset) {

        NormalizedResult normalizedResult = new NormalizedResult();
        List<NormalizedRecord> mappedResult = new ArrayList<>();
        try {
            for (Result record : results.getResults()) {
                NormalizedRecord normalizedRecord = new NormalizedRecord();
                normalizedRecord.setRepository("dspace");
                normalizedRecord.setCollection(record.getResourceid());
                normalizedRecord.setDate("");
                normalizedRecord.setDescription(record.getDescription());
                normalizedRecord.setId(record.getResourceid());
                normalizedRecord.setFiletype(record.getResourcetype());
                normalizedRecord.setLocator(record.getHandle());
                normalizedRecord.setSource("");
                normalizedRecord.setTitle(record.getDefaultTitle());
                mappedResult.add(normalizedRecord);
            }
            normalizedResult.setRecords(mappedResult);
            NormalizedPager normalizedPager = new NormalizedPager();
            normalizedPager.setPagingIncrement("10");
            normalizedPager.setStartIndex(Integer.toString(results.getOffset()));
            normalizedPager.setTotalRecs(results.getCount());
            normalizedResult.setPager(normalizedPager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return normalizedResult;
    }

}
