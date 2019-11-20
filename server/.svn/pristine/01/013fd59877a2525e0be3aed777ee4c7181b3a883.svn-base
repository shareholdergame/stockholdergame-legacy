package com.stockholdergame.server.gamecore;

import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.ShareNotFoundException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;

import java.util.Map;
import java.util.Observer;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 17.58
 */
public interface CompetitorAccount extends Comparable<CompetitorAccount>, Observer {

    int getMoveOrder();

    boolean isOut();

    int getShareQuantity(Long shareId);

    Map<Long, Integer> getShareQuantities();

    int getLockedShareQuantity(Long shareId);

    int getCash();

    Compensation getCompensation(Long shareId);

    int getTotal();

    void buySellShare(Long shareId, int quantity) throws NotEnoughSharesException, SharesLockedException,
        ShareNotFoundException, NotEnoughFundsException;

    void out();

    void chargeCompensation(int compensation, Long shareId, boolean currentCompetitor);

    void withdrawal(Long shareId);

    int repurchase(int redemptionSum, Long shareId) throws NotEnoughSharesException, SharesLockedException, NotEnoughFundsException;

    <T> T getExtraData();

    boolean isWithdrawal(Long shareId);
}
