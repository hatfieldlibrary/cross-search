package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.dao.ExistdbSingleDao;
import edu.willamette.crossearch.model.NormalizedPager;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.model.existdb.Item;
import edu.willamette.crossearch.model.existdb.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExistDbSingleRepository implements RepositoryInterface {

    @Autowired
    ExistdbSingleDao existdbSingleDao;

    @Value("${record.count}")
    String setSize;

    @Override
    public NormalizedResult execQuery(String terms, String offset, String mode, String collections) {

        Result result = existdbSingleDao.execQuery(terms, offset, mode, collections);
        return normalize(result, offset);

    }

    private NormalizedResult normalize(Result results, String offset) {

        NormalizedResult normalizedResult = new NormalizedResult();
        List<NormalizedRecord> mappedResult = new ArrayList<>();
        ExistUtils existUtils = new ExistUtils();
        String total = results.getTotal();
        List<Item> items = results.getItem();
        for (Item item : items) {
            NormalizedRecord normalizedRecord = existUtils.getNormalizedRecord(item);
            mappedResult.add(normalizedRecord);
        }
        Map<String, List<NormalizedRecord>> map =
                mappedResult.stream()
                        .collect(Collectors.groupingBy(NormalizedRecord::getSource));

        normalizedResult.setRecords(map);
        NormalizedPager normalizedPager = new NormalizedPager();
        normalizedPager.setPagingIncrement(setSize);
        normalizedPager.setStartIndex(offset);
        normalizedPager.setTotalRecs(Integer.valueOf(total));
        normalizedResult.setPager(normalizedPager);
        return normalizedResult;
    }

}
