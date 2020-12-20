package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.*;
import com.stockholdergame.server.gamecore.exceptions.GameIsFinishedException;
import com.stockholdergame.server.gamecore.exceptions.IllegalMoveOrderException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.ShareNotFoundException;
import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;
import com.stockholdergame.server.gamecore.model.math.ArithmeticOperation;

import java.util.*;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public class GameStateImpl<T> extends Observable implements GameState {

    private PriceScale priceScale;

    private int movesQuantity;

    private int currentMoveNumber = 1;

    private int currentMoveOrder = 1;

    private boolean isFinished;

    private Map<Long, SharePrice> sharePriceMap = new TreeMap<>();

    private Map<Integer, CompetitorAccount> competitorAccountMap = new TreeMap<>();

    private T extraData;

    public GameStateImpl(int movesQuantity,
                         int currentMoveNumber,
                         int currentMoveOrder,
                         List<SharePrice> sharePrices,
                         List<CompetitorAccount> competitorAccounts,
                         PriceScale priceScale) {
        this.movesQuantity = movesQuantity;
        this.currentMoveNumber = currentMoveNumber;
        this.currentMoveOrder = currentMoveOrder;
        fillSharePricesMap(sharePrices);
        fillCompetitorAccountsMap(competitorAccounts);
        this.priceScale = priceScale;
    }

    private void fillCompetitorAccountsMap(List<CompetitorAccount> competitorAccounts) {
        for (CompetitorAccount competitorAccount : competitorAccounts) {
            addObserver(competitorAccount);
            competitorAccountMap.put(competitorAccount.getMoveOrder(), competitorAccount);
        }
    }

    private void fillSharePricesMap(List<SharePrice> sharePrices) {
        for (SharePrice sharePrice : sharePrices) {
            sharePriceMap.put(sharePrice.getShareId(), sharePrice);
        }
    }

    public int getMovesQuantity() {
        return movesQuantity;
    }

    public int getCurrentMoveNumber() {
        return currentMoveNumber;
    }

    public int getCurrentMoveOrder() {
        return currentMoveOrder;
    }

    public int getCompetitorsQuantity() {
        return competitorAccountMap.size();
    }

    @Override
    public Set<Long> getShareIds() {
        return Collections.unmodifiableSortedSet(new TreeSet<>(sharePriceMap.keySet()));
    }

    @Override
    public void markAsBankrupt(int moveOrder) throws IllegalMoveOrderException {
        if (moveOrder == currentMoveOrder) {
            throw new IllegalMoveOrderException(moveOrder);
        }
        CompetitorAccount ca = competitorAccountMap.get(moveOrder);
        if (calculateTotalSharesQuantity(ca) == 0 && ca.getCash() < getCheapestSharePrice()) {
            ca.out();
        }
    }

    @Override
    public int repurchase(int moveOrder, int redemptionSum, Long shareId)
            throws NotEnoughSharesException, SharesLockedException, NotEnoughFundsException {
        CompetitorAccount ca = competitorAccountMap.get(moveOrder);
        if (ca.isWithdrawal(shareId)) {
            return competitorAccountMap.get(moveOrder).repurchase(redemptionSum, shareId);
        } else {
            return 0;
        }
    }

    @Override
    public CompetitorAccount getCurrentCompetitorState() {
        return competitorAccountMap.get(currentMoveOrder);
    }

    @Override
    public T getExtraData() {
        return extraData;
    }

    public void setExtraData(T extraData) {
        this.extraData = extraData;
    }

    public int getSharePrice(Long shareId) {
        return sharePriceMap.containsKey(shareId) ? sharePriceMap.get(shareId).getValue() : 0;
    }

    @Override
    public Map<Long, Integer> getSharePrices() {
        Map<Long, Integer> priceMap = new TreeMap<>();
        for (Map.Entry<Long, SharePrice> entry : sharePriceMap.entrySet()) {
            priceMap.put(entry.getKey(), entry.getValue().getValue());
        }
        return Collections.unmodifiableMap(priceMap);
    }

    @Override
    public List<SharePrice> getSharePricesOrderedByRepurchaseSumAndOldPrice() {
        List<SharePrice> ordered = new ArrayList<>();
        for (final SharePrice sharePrice : sharePriceMap.values()) {
            ordered.add(new SharePrice() {

                SharePrice orig = sharePrice;

                @Override
                public int getValue() {
                    return orig.getValue();
                }

                @Override
                public int getOldValue() {
                    return orig.getOldValue();
                }

                @Override
                public void setValue(int value) throws SharePriceAlreadyChangedException {
                    throw new UnsupportedOperationException("Operation 'setValue' is not supported");
                }

                @Override
                public int getRepurchaseSum() {
                    return orig.getRepurchaseSum();
                }

                @Override
                public void setRepurchaseSum(int value) {
                    throw new UnsupportedOperationException("Operation 'setRepurchaseSum' is not supported");
                }

                @Override
                public Long getShareId() {
                    return orig.getShareId();
                }
            });
        }

        Collections.sort(ordered, new Comparator<SharePrice>() {
            @Override
            public int compare(SharePrice sharePrice, SharePrice sharePrice1) {
                int result = sharePrice.getRepurchaseSum() - sharePrice1.getRepurchaseSum();
                if (result == 0) {
                    result = sharePrice.getOldValue() - sharePrice1.getOldValue();
                }
                return result;
            }
        });
        return ordered;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setSharePrice(Long shareId, ArithmeticOperation operation, int operandValue) throws ShareNotFoundException,
                                                                                                    SharePriceAlreadyChangedException,
                                                                                                    GameIsFinishedException,
                                                                                                    NotEnoughSharesException,
                                                                                                    SharesLockedException,
                                                                                                    NotEnoughFundsException {
        if (isFinished) {
            throw new GameIsFinishedException();
        }

        SharePrice sharePrice = sharePriceMap.get(shareId);
        if (sharePrice == null) {
            throw new ShareNotFoundException(shareId);
        }

        if (operation == null || operandValue <= 0) {
            return;
        }

        int oldPrice = sharePrice.getValue();
        int calculatedPrice = Rounder.round(operation.execute(oldPrice, operandValue), priceScale.getSharePriceStep(), priceScale.isRoundingDown());
        if (calculatedPrice == oldPrice) {
            return;
        }
        sharePrice.setValue(trimToScale(calculatedPrice));
        notifyCompetitorAccounts();

        boolean isZeroing = false;
        if (calculatedPrice < priceScale.getSharePriceStep()) {
            int repurchaseSum = CompensationCalculator.calculateRepurchaseSum(sharePrice.getValue(), calculatedPrice);
            sharePriceMap.get(shareId).setRepurchaseSum(repurchaseSum);
            isZeroing = true;
        }

        for (CompetitorAccount competitorAccount : competitorAccountMap.values()) {
            if (competitorAccount.isOut()) {
                continue;
            }
            boolean isCurrentCompetitor = currentMoveOrder == competitorAccount.getMoveOrder();

            if (isZeroing && !isCurrentCompetitor) {
                competitorAccount.withdrawal(shareId);
            } else {
                int compensation = CompensationCalculator.calculateCompensation(oldPrice, sharePrice.getValue(), calculatedPrice,
                        competitorAccount.getShareQuantity(shareId), isCurrentCompetitor);
                competitorAccount.chargeCompensation(compensation, shareId, isCurrentCompetitor);
            }
        }
    }

    private int trimToScale(int value) {
        return value < priceScale.getSharePriceStep() ? priceScale.getSharePriceStep() :
                value > priceScale.getMaxSharePrice() ? priceScale.getMaxSharePrice() : value;
    }

    private void notifyCompetitorAccounts() {
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public void buySellShare(Long shareId, int quantity)
            throws NotEnoughSharesException, SharesLockedException, ShareNotFoundException,
        NotEnoughFundsException, GameIsFinishedException, IllegalMoveOrderException {
        buySellShare(currentMoveOrder, shareId, quantity);
    }

    @Override
    public void buySellShare(int moveOrder, Long shareId, int quantity) throws NotEnoughSharesException, SharesLockedException,
                                                                               ShareNotFoundException, NotEnoughFundsException,
                                                                               GameIsFinishedException, IllegalMoveOrderException {
        if (isFinished) {
            throw new GameIsFinishedException();
        }

        CompetitorAccount ca = competitorAccountMap.get(moveOrder);
        if (moveOrder != currentMoveOrder && !ca.isOut()) {
            throw new IllegalMoveOrderException(moveOrder);
        }

        ca.buySellShare(shareId, quantity);
    }

    public void commit() {
        if (isFinished) {
            return;
        }
        for (SharePrice sharePrice : sharePriceMap.values()) {
            ((TransactionSupport) sharePrice).commit();
        }
        for (CompetitorAccount competitorAccount : competitorAccountMap.values()) {
            ((TransactionSupport) competitorAccount).commit();
        }
        switchToNextMover(currentMoveNumber, currentMoveOrder);
    }

    private void switchToNextMover(int currentMoveNumber, int currentMoveOrder) {
        if (currentMoveOrder == competitorAccountMap.size()) {
            if (currentMoveNumber == movesQuantity) {
                isFinished = true;
                return;
            }
            currentMoveNumber++;
            currentMoveOrder = 1;
        } else {
            currentMoveOrder++;
        }
        CompetitorAccount nextCompetitorAccount = competitorAccountMap.get(currentMoveOrder);
        if (nextCompetitorAccount.isOut()) {
            switchToNextMover(currentMoveNumber, currentMoveOrder);
        } else if (calculateTotalSharesQuantity(nextCompetitorAccount) == 0 && nextCompetitorAccount.getCash() < getCheapestSharePrice()) {
            nextCompetitorAccount.out();
            switchToNextMover(currentMoveNumber, currentMoveOrder);
        } else if (currentMoveOrder == this.currentMoveOrder) {
            isFinished = true;
        } else {
            this.currentMoveNumber = currentMoveNumber;
            this.currentMoveOrder = currentMoveOrder;
        }
    }

    private int getCheapestSharePrice() {
        int cheapestPrice = priceScale.getMaxSharePrice();
        for (Long shareId : getShareIds()) {
            int sharePrice = getSharePrice(shareId);
            if (sharePrice < cheapestPrice) {
                cheapestPrice = sharePrice;
            }
        }
        return cheapestPrice;
    }

    private int calculateTotalSharesQuantity(CompetitorAccount nextCompetitorAccount) {
        int totalSharesQuantity = 0;
        for (Long shareId : getShareIds()) {
            totalSharesQuantity += nextCompetitorAccount.getShareQuantity(shareId);
        }
        return totalSharesQuantity;
    }

    public void rollback() {
        if (isFinished) {
            return;
        }
        for (SharePrice sharePrice : sharePriceMap.values()) {
            ((TransactionSupport) sharePrice).rollback();
        }
        for (CompetitorAccount competitorAccount : competitorAccountMap.values()) {
            ((TransactionSupport) competitorAccount).rollback();
        }
    }

    public CompetitorAccount getCompetitorAccount(int moveOrder) {
        return new UnmodifiableCompetitorAccount(competitorAccountMap.get(moveOrder));
    }

    @Override
    public Set<CompetitorAccount> getCompetitorAccounts() {
        SortedSet<CompetitorAccount> competitorAccounts = new TreeSet<>();
        for (CompetitorAccount competitorAccount : competitorAccountMap.values()) {
            competitorAccounts.add(new UnmodifiableCompetitorAccount(competitorAccount));
        }
        return Collections.unmodifiableSortedSet(competitorAccounts);
    }

    @Override
    public boolean isLastMove() {
        return currentMoveNumber == movesQuantity;
    }

    private static class UnmodifiableCompetitorAccount implements CompetitorAccount {

        private CompetitorAccount competitorAccount;

        public UnmodifiableCompetitorAccount(CompetitorAccount competitorAccount) {
            this.competitorAccount = competitorAccount;
        }

        public int getMoveOrder() {
            return competitorAccount.getMoveOrder();
        }

        public boolean isOut() {
            return competitorAccount.isOut();
        }

        public int getShareQuantity(Long shareId) {
            return competitorAccount.getShareQuantity(shareId);
        }

        @Override
        public Map<Long, Integer> getShareQuantities() {
            return competitorAccount.getShareQuantities();
        }

        public int getLockedShareQuantity(Long shareId) {
            return competitorAccount.getLockedShareQuantity(shareId);
        }

        public int getCash() {
            return competitorAccount.getCash();
        }

        @Override
        public Compensation getCompensation(Long shareId) {
            return competitorAccount.getCompensation(shareId);
        }

        public int getTotal() {
            return competitorAccount.getTotal();
        }

        public void buySellShare(Long shareId, int quantity) {
            throw new UnsupportedOperationException("Method 'buySellShare' is not supported");
        }

        public void out() {
            throw new UnsupportedOperationException("Method 'out' is not supported");
        }

        public void chargeCompensation(int compensation, Long shareId, boolean currentCompetitor) {
            throw new UnsupportedOperationException("Method 'chargeCompensation' is not supported");
        }

        @Override
        public void withdrawal(Long shareId) {
            throw new UnknownFormatConversionException("Method 'withdrawal' is not supported");
        }

        @Override
        public int repurchase(int redemptionSum, Long shareId) {
            throw new UnknownFormatConversionException("Method 'repurchase' is not supported");
        }

        @Override
        public <X> X getExtraData() {
            return competitorAccount.getExtraData();
        }

        @Override
        public boolean isWithdrawal(Long shareId) {
            return competitorAccount.isWithdrawal(shareId);
        }

        @Override
        public int compareTo(CompetitorAccount o) {
            return competitorAccount.compareTo(o);
        }

        @Override
        public void update(Observable o, Object arg) {
            competitorAccount.update(o, arg);
        }
    }
}
