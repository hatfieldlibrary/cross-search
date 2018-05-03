package edu.willamette.crossearch.dao;

import edu.willamette.crossearch.model.NormalizedPager;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import edu.willamette.crossearch.model.NormalizedResultMap;
import io.reactivex.Flowable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatefulRecordHolder {

    Logger log = LogManager.getLogger(StatefulRecordHolder.class);

    private final NormalizedResult normalizedResult = new NormalizedResult();
    private final Map<String, List<NormalizedRecord>> combinedRecords = new HashMap<>();
    private final NormalizedPager combinedPager = new NormalizedPager();
    private Integer total = 0;

    public Flowable<NormalizedResult> combineResult(NormalizedResult result, String offset, String increment) {
        return Flowable.fromCallable(() -> getCombinedResult(result, offset, increment));
    }


    private NormalizedResult getCombinedResult(NormalizedResult result, String offset, String increment) {

        // If the repository query produced no results, return the current result state.
        if(result.getRecords() == null) {
            return normalizedResult;
        }

        log.debug("Getting combined result");
        if (result.getPager().getTotalRecs() > total) {
            total = result.getPager().getTotalRecs();
            combinedPager.setTotalRecs(total);
        }

        List<String> keys = new ArrayList<>(result.getRecords().keySet());
        for (String key :  keys) {
            combinedRecords.put(key, result.getRecords().get(key));
        }

        normalizedResult.setRecords(combinedRecords);
        combinedPager.setPagingIncrement(increment);
        combinedPager.setStartIndex(offset);
        normalizedResult.setPager(combinedPager);


        return normalizedResult;
    }

}
