package com.stockholdergame.server.gamecore;

/**
 * @author Alexander Savin
 *         Date: 19.6.12 21.51
 */
public interface PriceScale {

    int getMaxSharePrice();

    int getSharePriceStep();

    boolean isRoundingDown();
}
