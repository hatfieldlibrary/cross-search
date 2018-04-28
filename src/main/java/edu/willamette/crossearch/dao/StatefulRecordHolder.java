package edu.willamette.crossearch.dao;

import edu.willamette.crossearch.model.NormalizedPager;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.repository.ExistDbRepository;
import io.reactivex.Flowable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StatefulRecordHolder {

    Logger log = LogManager.getLogger(StatefulRecordHolder.class);

    private final NormalizedResult normalizedResult = new NormalizedResult();
    private final List<NormalizedRecord> combinedRecords = new ArrayList<>();
    private final NormalizedPager combinedPager = new NormalizedPager();
    private Integer total = 0;

    public Flowable<NormalizedResult> combineResult(NormalizedResult result, String offset, String increment) {
        return Flowable.fromCallable(() -> getCombinedResult(result, offset, increment));
    }


    private NormalizedResult getCombinedResult(NormalizedResult result, String offset, String increment) {

        log.debug("Getting combined result");
        if (result.getPager().getTotalRecs() > total) {
            total = result.getPager().getTotalRecs();
            combinedPager.setTotalRecs(total);
        }
        for (NormalizedRecord record : result.getRecords()) {
            combinedRecords.add(record);
        }
        combinedPager.setPagingIncrement(increment);
        combinedPager.setStartIndex(offset);
        normalizedResult.setPager(combinedPager);
        normalizedResult.setRecords(combinedRecords);

        return normalizedResult;
    }
}
