package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.dao.ContentdmDao;
import edu.willamette.crossearch.model.NormalizedPager;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.model.contentdm.Record;
import edu.willamette.crossearch.model.contentdm.CdmResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ContentdmRepository implements RepositoryInterface {

    Logger log = LogManager.getLogger(ContentdmRepository.class);


    @Autowired
    ContentdmDao contentdmDao;

    @Override
    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        log.debug("Contentdm query");
        CdmResult cdmResult = contentdmDao.execQuery(terms, reduceOffset(offset), mode, collections);
        return normalize(cdmResult);
    }

    private NormalizedResult normalize(CdmResult results) {

        NormalizedResult normalizedResult = new NormalizedResult();
        List<NormalizedRecord> mappedResult = new ArrayList<>();
        try {
            for (Record record : results.getRecords()) {
                NormalizedRecord normalizedRecord = new NormalizedRecord();
                normalizedRecord.setRepository("cdm");
                normalizedRecord.setCollection(record.getCollection());
                normalizedRecord.setDate(record.getDate());
                normalizedRecord.setDescription(record.getDescri());
                normalizedRecord.setId(Integer.toString(record.getPointer()));
                normalizedRecord.setFiletype(record.getFiletype());
                normalizedRecord.setLocator(record.getFind());
                normalizedRecord.setSource(record.getSource());
                normalizedRecord.setTitle(record.getTitle());
                mappedResult.add(normalizedRecord);
            }

            Map<String, List<NormalizedRecord>> map =
                    mappedResult.stream()
                            .collect(Collectors.groupingBy(NormalizedRecord::getSource));

            normalizedResult.setRecords(map);
            NormalizedPager normalizedPager = new NormalizedPager();
            normalizedPager.setPagingIncrement(results.getPager().getMaxrecs());
            normalizedPager.setStartIndex(increaseOffset(results.getPager().getStart()));
            normalizedPager.setTotalRecs(results.getPager().getTotal());
            normalizedResult.setPager(normalizedPager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return normalizedResult;
    }


    private Integer compareResult(NormalizedRecord r1, NormalizedRecord r2) {
        return r1.getSource().compareTo(r2.getSource());
    }


    /**
     * This function adjust the offset value down by 1 to
     * accommodate CONTENTdm's zero-based pagination.
     * @param offset
     * @return
     */
    private String reduceOffset(String offset) {

        Integer tmp = Integer.valueOf(offset);
        return Integer.toString(tmp - 1);
    }

    /**
     * This function increases the offset by one to
     * accommodate the the cview data API 1-based
     * pagination.
     * @param offset
     * @return
     */
    private String increaseOffset(String offset) {
        Integer tmp = Integer.valueOf(offset);
        return Integer.toString(tmp + 1);
    }

}
