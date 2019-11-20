package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.BeforeCommitListener;
import com.stockholdergame.server.gamecore.Compensation;
import com.stockholdergame.server.gamecore.CompetitorAccount;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.MovePerformer;
import com.stockholdergame.server.gamecore.SharePrice;
import com.stockholdergame.server.gamecore.exceptions.GameIsFinishedException;
import com.stockholdergame.server.gamecore.exceptions.IllegalMoveOrderException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.ShareNotFoundException;
import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;
import com.stockholdergame.server.gamecore.model.BuySellAction;
import com.stockholdergame.server.gamecore.model.MoveData;
import com.stockholdergame.server.gamecore.model.PriceChangeAction;
import com.stockholdergame.server.gamecore.model.result.BuySellActionResult;
import com.stockholdergame.server.gamecore.model.result.BuySellStepResult;
import com.stockholdergame.server.gamecore.model.result.MoveResult;
import com.stockholdergame.server.gamecore.model.result.PriceChangeActionResult;
import com.stockholdergame.server.gamecore.model.result.PriceChangeStepResult;
import com.stockholdergame.server.gamecore.model.result.RepurchaseResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Savin
 *         Date: 19.6.12 23.54
 */
public class MovePerformerImpl implements MovePerformer {

    private Set<BeforeCommitListener> beforeCommitListeners = new HashSet<>();

    @Override
    public void addBeforeCommitListener(BeforeCommitListener listener) {
        beforeCommitListeners.add(listener);
    }

    @Override
    public void removeBeforeCommitListener(BeforeCommitListener listener) {
        if (beforeCommitListeners.contains(listener)) {
            beforeCommitListeners.remove(listener);
        }
    }

    public MoveResult doMove(MoveData moveData, GameState gameState) throws NotEnoughSharesException, SharesLockedException,
                                                                            GameIsFinishedException, ShareNotFoundException,
                                                                            NotEnoughFundsException, SharePriceAlreadyChangedException {
        Lock lock = new ReentrantLock();
        lock.lock();

        try {
            boolean isLastMove = gameState.isLastMove();
            BuySellStepResult firstBuySellStepResult = null;
            PriceChangeStepResult priceChangeStepResult;
            BuySellStepResult lastBuySellStepResult = null;
            if (!isLastMove) {
                performBuySellStep(moveData.getFirstBuySellStepActions(), gameState);
                firstBuySellStepResult = createBuySellActionResults(moveData.getFirstBuySellStepActions(), gameState);
            }
            Map<Integer, RepurchaseResult> repurchaseResultMap = performPriceChangeStep(moveData.getPriceChangeActions(), gameState);
            priceChangeStepResult = createPriceChangeResults(repurchaseResultMap, gameState);
            if (!isLastMove) {
                performBuySellStep(moveData.getLastBuySellStepActions(), gameState);
                lastBuySellStepResult = createBuySellActionResults(moveData.getLastBuySellStepActions(), gameState);
            }
            MoveResult moveResult = new MoveResult(firstBuySellStepResult, priceChangeStepResult, lastBuySellStepResult);
            for (BeforeCommitListener beforeCommitListener : beforeCommitListeners) {
                 beforeCommitListener.beforeCommit(moveData, gameState, moveResult);
            }
            gameState.commit();
            return moveResult;
        } catch (NotEnoughSharesException | SharesLockedException | GameIsFinishedException | ShareNotFoundException
            | SharePriceAlreadyChangedException | NotEnoughFundsException e) {
            gameState.rollback();
            throw e;
        } catch (Exception e) {
            gameState.rollback();
            throw new MovePerformerException(e);
        } finally {
            lock.unlock();
        }
    }

    private PriceChangeStepResult createPriceChangeResults(Map<Integer, RepurchaseResult> bankruptingProcedureResultMap,
                                                           GameState gameState) {
        Set<PriceChangeActionResult> priceChangeActionResults = new HashSet<>();
        Set<Long> shareIds = gameState.getShareIds();
        for (Long shareId : shareIds) {
            Map<Integer, Compensation> competitorCompensationsMap = createCompensationsMap(gameState, shareId);

            PriceChangeActionResult actionResult = new PriceChangeActionResult(shareId,
                    gameState.getSharePrice(shareId), competitorCompensationsMap);
            priceChangeActionResults.add(actionResult);
        }
        Map<Integer, Integer> competitorCashMap = createCashMap(gameState);

        return new PriceChangeStepResult(priceChangeActionResults, competitorCashMap, bankruptingProcedureResultMap);
    }

    private Map<Integer, Compensation> createCompensationsMap(GameState gameState, Long shareId) {
        Map<Integer, Compensation> competitorCompensationsMap = new HashMap<>();
        Set<CompetitorAccount> competitorAccounts = gameState.getCompetitorAccounts();
        for (CompetitorAccount competitorAccount : competitorAccounts) {
            Compensation compensation = competitorAccount.getCompensation(shareId);
            if (compensation != null) {
                competitorCompensationsMap.put(competitorAccount.getMoveOrder(), compensation);
            }
        }
        return competitorCompensationsMap;
    }

    private Map<Integer, Integer> createCashMap(GameState gameState) {
        Map<Integer, Integer> competitorCashMap = new HashMap<>(gameState.getCompetitorsQuantity());
        Set<CompetitorAccount> competitorAccounts = gameState.getCompetitorAccounts();
        for (CompetitorAccount competitorAccount : competitorAccounts) {
            competitorCashMap.put(competitorAccount.getMoveOrder(), competitorAccount.getCash());
        }
        return competitorCashMap;
    }

    private BuySellStepResult createBuySellActionResults(Set<BuySellAction> buySellStepActions,
                                                         GameState gameState) {
        Set<BuySellActionResult> buySellActionResults = new TreeSet<>();
        for (BuySellAction buySellStepAction : buySellStepActions) {
            CompetitorAccount ca = gameState.getCompetitorAccount(gameState.getCurrentMoveOrder());
            Long shareId = buySellStepAction.getShareId();

            BuySellActionResult buySellActionResult = new BuySellActionResult(shareId,
                    buySellStepAction.getBuySellQuantity(), ca.getShareQuantity(shareId),
                    ca.getLockedShareQuantity(shareId));
            buySellActionResults.add(buySellActionResult);
        }
        Map<Integer, Integer> competitorCashMap = createCashMap(gameState);
        return new BuySellStepResult(buySellActionResults, competitorCashMap);
    }

    private void performBuySellStep(Set<BuySellAction> buySellActions, GameState gameState)
            throws NotEnoughSharesException, SharesLockedException, GameIsFinishedException, ShareNotFoundException,
                   NotEnoughFundsException, IllegalMoveOrderException {
        for (BuySellAction buySellAction : buySellActions) {
            gameState.buySellShare(buySellAction.getShareId(), buySellAction.getBuySellQuantity());
        }
    }

    private Map<Integer, RepurchaseResult> performPriceChangeStep(SortedSet<PriceChangeAction> priceChangeActions, GameState gameState)
            throws GameIsFinishedException, SharePriceAlreadyChangedException, ShareNotFoundException,
                   NotEnoughFundsException, NotEnoughSharesException, SharesLockedException, IllegalMoveOrderException {
        for (PriceChangeAction priceChangeAction : priceChangeActions) {
            gameState.setSharePrice(priceChangeAction.getShareId(), priceChangeAction.getArithmeticOperation(), priceChangeAction.getOperandValue());
        }

        return repurchaseShares(gameState);
    }

    private Map<Integer, RepurchaseResult> repurchaseShares(GameState gameState)
            throws NotEnoughSharesException, SharesLockedException, NotEnoughFundsException, IllegalMoveOrderException {
        Map<Integer, RepurchaseResult> repurchaseResultMap = new TreeMap<>();
        for (CompetitorAccount competitorAccount : gameState.getCompetitorAccounts()) {
            int moveOrder = competitorAccount.getMoveOrder();
            if (!competitorAccount.isOut() && competitorAccount.getMoveOrder() != gameState.getCurrentMoveOrder()
                    && isSharesWithdraw(competitorAccount, gameState.getShareIds())) {
                SortedSet<BuySellActionResult> buySellStepResults = startRedemptionProcedure(gameState, moveOrder);
                if (buySellStepResults.size() > 0) {
                    repurchaseResultMap.put(moveOrder, new RepurchaseResult(buySellStepResults, competitorAccount.getCash()));
                }
            }
        }
        return repurchaseResultMap;
    }

    private boolean isSharesWithdraw(CompetitorAccount competitorAccount, Set<Long> shareIds) {
        boolean isWithdraw = false;
        for (Long shareId : shareIds) {
            isWithdraw = competitorAccount.isWithdrawal(shareId);
            if (isWithdraw) {
                break;
            }
        }
        return isWithdraw;
    }

    private SortedSet<BuySellActionResult> startRedemptionProcedure(GameState gameState, int moveOrder)
            throws NotEnoughSharesException, SharesLockedException, NotEnoughFundsException, IllegalMoveOrderException {
        SortedSet<BuySellActionResult> buySellStepResults = new TreeSet<>();
        List<SharePrice> ordered = gameState.getSharePricesOrderedByRedemptionSumAndOldPrice();
        for (SharePrice sharePrice : ordered) {
            int repurchasedQuantity = 0;
            if (gameState.getCompetitorAccount(moveOrder).isWithdrawal(sharePrice.getShareId())
                && sharePrice.getRedemptionSum() > 0) {
                repurchasedQuantity = gameState.repurchase(moveOrder, sharePrice.getRedemptionSum(), sharePrice.getShareId());
                if (moveOrder - 1 == gameState.getCurrentMoveOrder()) {
                    gameState.markAsBankrupt(moveOrder);
                }
            }
            int shareQuantity = gameState.getCompetitorAccount(moveOrder).getShareQuantity(sharePrice.getShareId());
            BuySellActionResult buySellActionResult = new BuySellActionResult(sharePrice.getShareId(), repurchasedQuantity, shareQuantity, 0);
            buySellStepResults.add(buySellActionResult);
        }
        return buySellStepResults;
    }
}
