package com.stockholdergame.server.gamebot.domain;

import com.stockholdergame.server.dto.game.variant.CardDto;

import java.util.Map;

/**
 *
 */
public class MoveDecision {

    private Map<Long, Float> firstStepShares;

    private CardDto suggestedCard;

    private Map<Long, Long> sharesOperationMap;

    private Map<Long, Float> lastStepShares;

    public MoveDecision(Map<Long, Float> firstStepShares, CardDto suggestedCard, Map<Long, Long> sharesOperationMap, Map<Long, Float> lastStepShares) {
        this.firstStepShares = firstStepShares;
        this.suggestedCard = suggestedCard;
        this.sharesOperationMap = sharesOperationMap;
        this.lastStepShares = lastStepShares;
    }

    public CardDto getSuggestedCard() {
        return suggestedCard;
    }

    public Map<Long, Long> getSharesOperationMap() {
        return sharesOperationMap;
    }

    public Map<Long, Float> getFirstStepShares() {
        return firstStepShares;
    }

    public Map<Long, Float> getLastStepShares() {
        return lastStepShares;
    }
}
