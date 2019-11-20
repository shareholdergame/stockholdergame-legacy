package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.SharePrice;
import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import com.stockholdergame.server.gamecore.TransactionSupport;

/**
 * @author Alexander Savin
 *         Date: 17.6.12 10.50
 */
public class SharePriceImpl implements SharePrice, TransactionSupport {

    private Long shareId;

    private int oldValue = 0;

    private int value = 0;

    private int priceChangeCounter = 0;

    private int redemptionSum = 0;

    public SharePriceImpl(Long shareId, int value) {
        this.shareId = shareId;
        this.value = value;
    }

    public Long getShareId() {
        return shareId;
    }

    public int getValue() {
        return value;
    }

    public int getOldValue() {
        return oldValue;
    }

    public void setValue(int newValue) throws SharePriceAlreadyChangedException {
        if (priceChangeCounter > 0) {
            throw new SharePriceAlreadyChangedException(shareId);
        }

        this.oldValue = this.value;
        this.value = newValue;

        priceChangeCounter++;
    }

    @Override
    public int getRedemptionSum() {
        return redemptionSum;
    }

    @Override
    public void setRedemptionSum(int value) {
        if (this.redemptionSum > 0 || value <= 0) {
            return;
        }
        this.redemptionSum = value;
    }

    public void commit() {
        if (priceChangeCounter > 0) {
            priceChangeCounter = 0;
            redemptionSum = 0;
        }
    }

    public void rollback() {
        if (priceChangeCounter > 0) {
            value = oldValue;
            priceChangeCounter = 0;
            redemptionSum = 0;
        }
    }
}
