package edu.willamette.crossearch.dao;

import edu.willamette.crossearch.model.NormalizedPager;
import edu.willamette.crossearch.model.NormalizedRecord;
import edu.willamette.crossearch.model.NormalizedResult;
import io.reactivex.Flowable;

import java.util.ArrayList;
import java.util.List;

public class StatefulRecordHolder {

    private final NormalizedResult normalizedResult = new NormalizedResult();
    private final List<NormalizedRecord> combinedRecords = new ArrayList<>();
    private final NormalizedPager combinedPager = new NormalizedPager();
    private Integer total = 0;

    public Flowable<NormalizedResult> combineResult(NormalizedResult result, String offset, String increment) {
        return Flowable.fromCallable(() -> getCombinedResult(result, offset, increment));
    }


    private NormalizedResult getCombinedResult(NormalizedResult result, String offset, String increment) {

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
