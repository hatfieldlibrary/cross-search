package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.dao.ExistdbDao;
import edu.willamette.crossearch.model.NormalizedPager;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.model.existdb.CollectionResults;
import edu.willamette.crossearch.model.existdb.CombinedResult;
import edu.willamette.crossearch.model.existdb.Item;

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
public class ExistDbRepository implements RepositoryInterface {

    Logger log = LogManager.getLogger(ExistDbRepository.class);



    @Autowired
    ExistdbDao existdbDao;

    @Value("${record.count}")
    String setSize;

    @Override
    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        log.debug("exist query");
        CombinedResult result = existdbDao.execQuery(terms, offset, mode, collections);
        return normalize(result, offset);

    }

    private NormalizedResult normalize(CombinedResult results, String offset) {

        log.debug(results.getCollectionResults());
        NormalizedResult normalizedResult = new NormalizedResult();
        List<NormalizedRecord> mappedResult = new ArrayList<>();
        Integer total = 0;
        try {
            ExistUtils existUtils = new ExistUtils();
            for (CollectionResults coll : results.getCollectionResults()) {
                total = compareTotal(total, coll.getResult().getTotal());
                if (coll.getResult().getItem() != null) {
                    for (Item item : coll.getResult().getItem()) {
                        mappedResult.add(existUtils.getNormalizedRecord(item));
                    }
                }
            }

            Map<String, List<NormalizedRecord>> map =
                    mappedResult.stream()
                            .collect(Collectors.groupingBy(NormalizedRecord::getSource));

            normalizedResult.setRecords(map);
            NormalizedPager normalizedPager = new NormalizedPager();
            normalizedPager.setPagingIncrement(setSize);
            normalizedPager.setStartIndex(offset);
            normalizedPager.setTotalRecs(total);
            normalizedResult.setPager(normalizedPager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return normalizedResult;
    }

    private Integer compareTotal(Integer total, String latestTotal) {

        if (total < Integer.valueOf(latestTotal)) {
            return Integer.valueOf(latestTotal);
        }
        return total;

    }

}
