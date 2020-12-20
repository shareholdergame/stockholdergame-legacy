package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.*;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.ShareNotFoundException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;

import org.apache.commons.lang3.Validate;

import java.util.*;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public class CompetitorAccountImpl<T> implements CompetitorAccount, TransactionSupport {

    private int moveOrder;

    private boolean isOut;

    private boolean isOutChanged;

    private int total = 0;

    private Cash cash;

    private Map<Long, ShareQuantity> shareQuantityMap = new TreeMap<>();

    private T extraData;

    public CompetitorAccountImpl(int moveOrder, boolean isOut, Cash cash, List<ShareQuantity> shareQuantities) {
        this.moveOrder = moveOrder;
        this.isOut = isOut;
        this.cash = cash;
        fillShareQuantityMap(shareQuantities);
        recalculateTotal();
    }

    private void fillShareQuantityMap(List<ShareQuantity> shareQuantities) {
        for (ShareQuantity shareQuantity : shareQuantities) {
            shareQuantityMap.put(shareQuantity.getSharePrice().getShareId(), shareQuantity);
        }
    }

    public int getMoveOrder() {
        return moveOrder;
    }

    public boolean isOut() {
        return isOut;
    }

    public int getShareQuantity(Long shareId) {
        return shareQuantityMap.containsKey(shareId) ? shareQuantityMap.get(shareId).getQuantity() : 0;
    }

    @Override
    public Map<Long, Integer> getShareQuantities() {
        Map<Long, Integer> quantityMap = new TreeMap<>();
        for (Map.Entry<Long, ShareQuantity> entry : shareQuantityMap.entrySet()) {
            quantityMap.put(entry.getKey(), entry.getValue().getQuantity());
        }
        return Collections.unmodifiableMap(quantityMap);
    }

    public int getLockedShareQuantity(Long shareId) {
        return shareQuantityMap.containsKey(shareId) ? shareQuantityMap.get(shareId).getLocked() : 0;
    }

    public int getCash() {
        return cash.getValue();
    }

    @Override
    public Compensation getCompensation(Long shareId) {
        return cash.getCompensation(shareId);
    }

    public int getTotal() {
        return total;
    }

    public void buySellShare(Long shareId, int buySellQuantity)
        throws NotEnoughSharesException, SharesLockedException, ShareNotFoundException, NotEnoughFundsException {

        if (buySellQuantity == 0) {
            return;
        }
        ShareQuantity shareQuantity = shareQuantityMap.get(shareId);
        if (shareQuantity == null) {
            throw new ShareNotFoundException(shareId);
        }

        if (buySellQuantity > 0) {
            int needFunds = buySellQuantity * shareQuantity.getSharePrice().getValue();
            cash.charge(-needFunds);
            shareQuantity.buySell(buySellQuantity);
        } else {
            int cashSum = Math.abs(buySellQuantity) * shareQuantity.getSharePrice().getValue();
            shareQuantity.buySell(buySellQuantity);
            cash.charge(cashSum);
        }
    }

    public void out() {
        if (total <= 0) {
            isOut = true;
            isOutChanged = true;
        }
    }

    public void chargeCompensation(int compensation, Long shareId, boolean currentCompetitor) {
        cash.chargeCompensation(compensation, shareId, !currentCompetitor);
        recalculateTotal();
    }

    @Override
    public void withdrawal(Long shareId) {
        shareQuantityMap.get(shareId).withdrawal();
        recalculateTotal();
    }

    @Override
    public int repurchase(int repurchaseSum, Long shareId) throws NotEnoughSharesException, SharesLockedException, NotEnoughFundsException {
        if (repurchaseSum == 0) {
            return 0;
        }
        ShareQuantity shareQuantity = shareQuantityMap.get(shareId);
        if (cash.getValue() >= repurchaseSum) {
            int repurchasedShareQuantity = (int) Math.abs(Math.floor(cash.getValue() / repurchaseSum));
            if (repurchasedShareQuantity > 0) {
                if (repurchasedShareQuantity > shareQuantity.getOldQuantity()) {
                    repurchasedShareQuantity = shareQuantity.getOldQuantity();
                }
                shareQuantity.buySell(repurchasedShareQuantity);
                cash.charge(-(repurchaseSum * repurchasedShareQuantity));
            }
        }
        recalculateTotal();
        return shareQuantity.getQuantity();
    }

    @Override
    public boolean isWithdrawal(Long shareId) {
        return shareQuantityMap.get(shareId).isWithdrawal();
    }

    @Override
    public T getExtraData() {
        return extraData;
    }

    public void setExtraData(T extraData) {
        this.extraData = extraData;
    }

    private void recalculateTotal() {
        int total = 0;
        for (Map.Entry<Long, ShareQuantity> entry : shareQuantityMap.entrySet()) {
            ShareQuantity shareQuantity = entry.getValue();
            int price = shareQuantity.getSharePrice().getValue();
            total += price * shareQuantity.getQuantity();
        }
        total += cash.getValue();
        this.total = total;
    }

    public void commit() {
        for (ShareQuantity shareQuantity : shareQuantityMap.values()) {
            ((TransactionSupport) shareQuantity).commit();
        }
        ((TransactionSupport) cash).commit();
    }

    public void rollback() {
        ((TransactionSupport) cash).rollback();
        for (ShareQuantity shareQuantity : shareQuantityMap.values()) {
            ((TransactionSupport) shareQuantity).rollback();
        }
        if (isOutChanged) {
            isOut = false;
            isOutChanged = false;
        }
        recalculateTotal();
    }

    @Override
    public int compareTo(CompetitorAccount o) {
        return this.moveOrder - o.getMoveOrder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitorAccountImpl that = (CompetitorAccountImpl) o;

        if (moveOrder != that.moveOrder) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return moveOrder;
    }

    @Override
    public void update(Observable o, Object arg) {
        Validate.isInstanceOf(GameState.class, o);

        recalculateTotal();
    }
}
