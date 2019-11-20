package com.stockholdergame.server.model.game.move;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MoveExtraData {

    private Long appliedCardId;

    private Map<Long, Long> priceOperationMap = new HashMap<Long, Long>();

    public Long getAppliedCardId() {
        return appliedCardId;
    }

    public void setAppliedCardId(Long appliedCardId) {
        this.appliedCardId = appliedCardId;
    }

    public Map<Long, Long> getPriceOperationMap() {
        return priceOperationMap;
    }
}
