package com.stockholdergame.server.gamecore.model;

import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Alexander Savin
 *         Date: 19.6.12 23.53
 */
public class MoveData<T> {

    private SortedSet<BuySellAction> firstBuySellStepActions;

    private SortedSet<PriceChangeAction> priceChangeActions;

    private SortedSet<BuySellAction> lastBuySellStepActions;

    private T extraData;

    public MoveData(Set<BuySellAction> firstBuySellStepActions,
                    Set<PriceChangeAction> priceChangeActions,
                    Set<BuySellAction> lastBuySellStepActions) {
            Validate.notEmpty(priceChangeActions);

            this.firstBuySellStepActions = firstBuySellStepActions != null ?
                    new TreeSet<>(firstBuySellStepActions) : null;
            this.priceChangeActions = new TreeSet<>(priceChangeActions);
            this.lastBuySellStepActions = lastBuySellStepActions != null ?
                    new TreeSet<>(lastBuySellStepActions) : null;
        }

    public Set<BuySellAction> getFirstBuySellStepActions() {
        return Collections.unmodifiableSortedSet(firstBuySellStepActions);
    }

    public SortedSet<PriceChangeAction> getPriceChangeActions() {
        return Collections.unmodifiableSortedSet(priceChangeActions);
    }

    public Set<BuySellAction> getLastBuySellStepActions() {
        return Collections.unmodifiableSortedSet(lastBuySellStepActions);
    }

    public T getExtraData() {
        return extraData;
    }

    public void setExtraData(T extraData) {
        this.extraData = extraData;
    }
}
