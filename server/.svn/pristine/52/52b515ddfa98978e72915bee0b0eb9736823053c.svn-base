package com.stockholdergame.server.gamecore.model.result;

import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Aliaksandr Savin
 */
public class PriceChangeStepResult extends AbstractStepResult {

    private Map<Long, PriceChangeActionResult> priceChangeActionResultMap;

    private SortedMap<Integer, RepurchaseResult> repurchaseResult;

    public PriceChangeStepResult(Set<PriceChangeActionResult> priceChangeActionResults,
                                 Map<Integer, Integer> competitorCashMap,
                                 Map<Integer, RepurchaseResult> repurchaseResult) {
        Validate.notEmpty(priceChangeActionResults);

        priceChangeActionResultMap = new HashMap<>(priceChangeActionResults.size());
        for (PriceChangeActionResult priceChangeActionResult : priceChangeActionResults) {
            priceChangeActionResultMap.put(priceChangeActionResult.getShareId(), priceChangeActionResult);
        }

        this.competitorCashMap = competitorCashMap != null ?
            new TreeMap<>(competitorCashMap) : null;
        this.repurchaseResult = repurchaseResult != null ?
                new TreeMap<>(repurchaseResult) : null;
    }

    public SortedSet<PriceChangeActionResult> getPriceChangeActionResults() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(priceChangeActionResultMap.values()));
    }

    public PriceChangeActionResult getPriceChangeActionResult(Long shareId) {
        return priceChangeActionResultMap.get(shareId);
    }

    public SortedMap<Integer, RepurchaseResult> getRepurchaseResult() {
        return Collections.unmodifiableSortedMap(repurchaseResult);
    }
}
