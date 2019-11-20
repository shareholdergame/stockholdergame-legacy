package com.stockholdergame.server.gamecore;

import com.stockholdergame.server.gamecore.exceptions.GameIsFinishedException;
import com.stockholdergame.server.gamecore.exceptions.IllegalMoveOrderException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughFundsException;
import com.stockholdergame.server.gamecore.exceptions.NotEnoughSharesException;
import com.stockholdergame.server.gamecore.exceptions.ShareNotFoundException;
import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import com.stockholdergame.server.gamecore.exceptions.SharesLockedException;
import com.stockholdergame.server.gamecore.model.math.ArithmeticOperation;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 11.41
 */
public interface GameState extends TransactionSupport {

    int getCurrentMoveNumber();

    int getCurrentMoveOrder();

    int getMovesQuantity();

    int getSharePrice(Long shareId);

    Map<Long, Integer> getSharePrices();

    List<SharePrice> getSharePricesOrderedByRedemptionSumAndOldPrice();

    void setSharePrice(Long shareId, ArithmeticOperation operation, int operandValue) throws ShareNotFoundException,
                                                                                             SharePriceAlreadyChangedException,
                                                                                             NotEnoughFundsException,
                                                                                             GameIsFinishedException,
                                                                                             NotEnoughSharesException,
                                                                                             SharesLockedException;

    void buySellShare(Long shareId, int quantity) throws NotEnoughSharesException, SharesLockedException,
                                                         ShareNotFoundException, NotEnoughFundsException,
                                                         GameIsFinishedException, IllegalMoveOrderException;

    void buySellShare(int moveOrder, Long shareId, int quantity) throws NotEnoughSharesException, SharesLockedException,
                                                                        ShareNotFoundException, NotEnoughFundsException,
                                                                        GameIsFinishedException, IllegalMoveOrderException;

    CompetitorAccount getCompetitorAccount(int moveOrder);

    Set<CompetitorAccount> getCompetitorAccounts();

    boolean isLastMove();

    boolean isFinished();

    int getCompetitorsQuantity();

    Set<Long> getShareIds();

    void markAsBankrupt(int moveOrder) throws IllegalMoveOrderException;

    int repurchase(int moveOrder, int redemptionSum, Long shareId) throws NotEnoughSharesException, SharesLockedException, NotEnoughFundsException;

    <T> T getExtraData();

    CompetitorAccount getCurrentCompetitorState();
}
