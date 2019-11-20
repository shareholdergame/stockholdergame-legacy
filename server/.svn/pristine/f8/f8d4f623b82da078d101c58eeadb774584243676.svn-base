package com.stockholdergame.server.gamecore.model.result;

import org.apache.commons.lang3.Validate;

/**
 * @author Alexander Savin
 *         Date: 25.8.12 21.04
 */
public class MoveResult {

    private BuySellStepResult firstBuySellStepResult;

    private PriceChangeStepResult priceChangeStepResult;

    private BuySellStepResult lastBuySellStepResult;

    public MoveResult(BuySellStepResult firstBuySellStepResult,
                      PriceChangeStepResult priceChangeStepResult,
                      BuySellStepResult lastBuySellStepResult) {
        Validate.notNull(priceChangeStepResult);

        this.firstBuySellStepResult = firstBuySellStepResult;
        this.priceChangeStepResult = priceChangeStepResult;
        this.lastBuySellStepResult = lastBuySellStepResult;
    }

    public BuySellStepResult getFirstBuySellStepResult() {
        return firstBuySellStepResult;
    }

    public PriceChangeStepResult getPriceChangeStepResult() {
        return priceChangeStepResult;
    }

    public BuySellStepResult getLastBuySellStepResult() {
        return lastBuySellStepResult;
    }
}
