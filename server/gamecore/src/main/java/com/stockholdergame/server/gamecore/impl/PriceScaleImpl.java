package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.PriceScale;

/**
 * @author Alexander Savin
 *         Date: 19.6.12 21.53
 */
public class PriceScaleImpl implements PriceScale {

    private int maxSharePrice;

    private int sharePriceStep;

    private boolean roundingDown;

    public PriceScaleImpl(int maxSharePrice, int sharePriceStep, boolean roundingDown) {
        this.maxSharePrice = maxSharePrice;
        this.sharePriceStep = sharePriceStep;
        this.roundingDown = roundingDown;
    }

    public int getMaxSharePrice() {
        return maxSharePrice;
    }

    public int getSharePriceStep() {
        return sharePriceStep;
    }

    @Override
    public boolean isRoundingDown() {
        return roundingDown;
    }
}
