package com.stockholdergame.server.gamecore;

import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public interface ShareQuantity {

    SharePrice getSharePrice();

    int getQuantity();

    int getOldQuantity();

    int getLocked();

    void buySell(int buySellQuantity) throws NotEnoughSharesException, SharesLockedException;

    void withdrawal();

    boolean isWithdrawal();
}
