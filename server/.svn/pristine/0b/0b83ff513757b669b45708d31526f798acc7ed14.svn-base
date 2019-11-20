package com.stockholdergame.server.gamecore.model.result;

import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Alexander Savin
 *         Date: 22.9.12 21.17
 */
public class BankruptingProcedureResult {

    private Map<Long, BuySellActionResult> buySellStepResultMap;

    private int competitorCash;

    public BankruptingProcedureResult(SortedSet<BuySellActionResult> buySellStepResults, int competitorCash) {
        Validate.notEmpty(buySellStepResults);

        buySellStepResultMap = new HashMap<>(buySellStepResults.size());
        for (BuySellActionResult buySellStepResult : buySellStepResults) {
            buySellStepResultMap.put(buySellStepResult.getShareId(), buySellStepResult);
        }

        this.competitorCash = competitorCash;
    }

    public BuySellActionResult getBuySellActionResult(Long shareId) {
        return buySellStepResultMap.get(shareId);
    }

    public SortedSet<BuySellActionResult> getBuySellStepResults() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(buySellStepResultMap.values()));
    }

    public int getCompetitorCash() {
        return competitorCash;
    }
}
