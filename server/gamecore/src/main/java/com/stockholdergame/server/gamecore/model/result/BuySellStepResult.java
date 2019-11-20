package com.stockholdergame.server.gamecore.model.result;

import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Aliaksandr Savin
 */
public class BuySellStepResult extends AbstractStepResult {

    private Map<Long, BuySellActionResult> buySellStepResultMap;

    public BuySellStepResult(Set<BuySellActionResult> buySellStepResults,
                             Map<Integer, Integer> competitorCashMap) {
        Validate.notEmpty(buySellStepResults);

        buySellStepResultMap = new HashMap<>(buySellStepResults.size());
        for (BuySellActionResult buySellActionResult : buySellStepResults) {
            buySellStepResultMap.put(buySellActionResult.getShareId(), buySellActionResult);
        }

        this.competitorCashMap = competitorCashMap != null ?
            new TreeMap<>(competitorCashMap) : null;
    }

    public SortedSet<BuySellActionResult> getBuySellActionResults() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(buySellStepResultMap.values()));
    }

    public BuySellActionResult getBuySellActionResult(Long shareId) {
        return buySellStepResultMap.get(shareId);
    }
}
