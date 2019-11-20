package com.stockholdergame.server.gamecore;

import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;

/**
 * @author Alexander Savin
 *         Date: 17.6.12 10.49
 */
public interface SharePrice extends Share {

    int getValue();

    int getOldValue();

    void setValue(int value) throws SharePriceAlreadyChangedException;

    int getRedemptionSum();

    void setRedemptionSum(int value);
}
