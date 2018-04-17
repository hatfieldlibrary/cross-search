package edu.willamette.crossearch.repository;

import edu.willamette.crossearch.model.NormalizedPager;
import org.springframework.stereotype.Component;

@Component
public class Pagination {

    private static final Integer increment = 10;

    public boolean hasNext(NormalizedPager pager, String offset) {

        return pager.getTotalRecs() > Integer.valueOf(offset) + increment;
    }

    public boolean hasPrev(String offset) {

        return Integer.valueOf(offset) > 1;
    }

    public String getOffset(String direction, String offset, NormalizedPager pager) {

        String newOffset = "0";
        if (direction.contentEquals("next")) {
            Integer offsetValue = Integer.valueOf(offset);
            if (pager.getTotalRecs() > offsetValue) {
                Integer tmpOffset = offsetValue + increment;
                newOffset = Integer.toString(tmpOffset);
            }
        }
        if (direction.contentEquals("prev")) {
            Integer offsetValue = Integer.valueOf(offset);
            if (offsetValue > 1) {
                Integer tmpOffset = offsetValue - increment;
                newOffset = Integer.toString(tmpOffset);
            }
        }
        return newOffset;
    }
}
