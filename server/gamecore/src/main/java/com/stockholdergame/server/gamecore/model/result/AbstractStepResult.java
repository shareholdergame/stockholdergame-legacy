package com.stockholdergame.server.gamecore.model.result;

import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * @author Aliaksandr Savin
 */
public abstract class AbstractStepResult {

    protected Map<Integer, Integer> competitorCashMap;

    public int getCompetitorCash(int moveOrder) {
        Validate.isTrue(competitorCashMap.containsKey(moveOrder));

        return competitorCashMap.get(moveOrder);
    }
}
