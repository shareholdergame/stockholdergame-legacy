package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.SharePrice;
import com.stockholdergame.server.gamecore.ShareQuantity;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;
import com.stockholdergame.server.gamecore.TransactionSupport;

/**
 * @author Alexander Savin
 *         Date: 19.6.12 23.54
 */
public class ShareQuantityImpl implements ShareQuantity, TransactionSupport {

    private SharePrice sharePrice;

    private int oldQuantity = 0;

    private int quantity = 0;

    private int locked = 0;

    private int buySellCounter = 0;

    private boolean isWithdrawal = false;

    public ShareQuantityImpl(SharePrice sharePrice, int quantity) {
        this.sharePrice = sharePrice;
        this.quantity = quantity;
    }

    public SharePrice getSharePrice() {
        return sharePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int getOldQuantity() {
        return oldQuantity;
    }

    public int getLocked() {
        return locked;
    }

    public boolean isWithdrawal() {
        return isWithdrawal;
    }

    public void buySell(int buySellQuantity) throws NotEnoughSharesException, SharesLockedException {
        int newQuantity = quantity + buySellQuantity;

        if (newQuantity < 0) {
            throw new NotEnoughSharesException("Share ID: " + this.sharePrice.getShareId() + ", buy-sell: " + buySellQuantity + ", quantity:" + quantity);
        } else if (locked > 0 && newQuantity < locked) {
            throw new SharesLockedException("Share ID: " + this.sharePrice.getShareId() + ", locked quantity:" + locked);
        }

        if (buySellCounter == 0) {
            oldQuantity = quantity;
        }

        quantity = newQuantity;
        locked = buySellQuantity > 0 ? buySellQuantity : 0;
        buySellCounter++;
    }

    @Override
    public void withdrawal() {
        if (quantity == 0) {
            return;
        }

        int newQuantity = 0;

        if (buySellCounter == 0) {
            oldQuantity = quantity;
        }

        quantity = newQuantity;
        buySellCounter++;
        isWithdrawal = true;
    }

    public void commit() {
        if (buySellCounter > 0) {
            this.locked = 0;
            this.buySellCounter = 0;
            this.isWithdrawal = false;
        }
    }

    public void rollback() {
        if (buySellCounter > 0) {
            this.quantity = this.oldQuantity;
            this.locked = 0;
            this.buySellCounter = 0;
            this.isWithdrawal = false;
        }
    }
}
