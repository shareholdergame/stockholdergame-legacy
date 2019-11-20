package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.CompetitorAccount;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.SharePrice;
import com.stockholdergame.server.gamecore.exceptions.GameIsFinishedException;
import com.stockholdergame.server.gamecore.exceptions.SharePriceAlreadyChangedException;
import com.stockholdergame.server.gamecore.model.math.ArithmeticOperation;
import junit.framework.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.stockholdergame.server.gamecore.impl.GameStateDataProvider.generateGameState;

/**
 * @author Alexander Savin
 *         Date: 10.6.12 13.09
 */
@Test
public class GameStateTest {

    @Test(dataProvider = "dataForTests")
    public void testBuySellStep(GameState gameState) throws Exception {
        gameState.buySellShare(1L, -1);
        gameState.buySellShare(2L, 1);
        CompetitorAccount ca = gameState.getCompetitorAccount(gameState.getCurrentMoveOrder());
        Assert.assertNotNull(ca);
        Assert.assertEquals(0, ca.getShareQuantity(1L));
        Assert.assertEquals(2, ca.getShareQuantity(2L));
        Assert.assertEquals(0, ca.getCash());
        Assert.assertEquals(400, ca.getTotal());
    }

    @Test(dataProvider = "dataForTests")
    public void testPriceChangeStep(GameState gameState) throws Exception {
        gameState.setSharePrice(1L, ArithmeticOperation.MULTIPLICATION, 2);
        gameState.setSharePrice(2L, ArithmeticOperation.DIVISION, 2);
        Assert.assertEquals(200, gameState.getSharePrice(1L));
        Assert.assertEquals(50, gameState.getSharePrice(2L));
        Set<CompetitorAccount> competitorAccounts = gameState.getCompetitorAccounts();
        for (CompetitorAccount competitorAccount : competitorAccounts) {
            Assert.assertEquals(competitorAccount.getMoveOrder() == 1 ? 50 : 0, competitorAccount.getCash());
            Assert.assertEquals(competitorAccount.getMoveOrder() == 1 ? 500 : 450, competitorAccount.getTotal());
        }
    }

    @Test(dataProvider = "dataForTests")
    public void testZeroing(GameState gameState) throws Exception {
        gameState.setSharePrice(1L, ArithmeticOperation.ADDITION, 50);
        gameState.setSharePrice(2L, ArithmeticOperation.SUBTRACTION, 150);
        Assert.assertEquals(150, gameState.getSharePrice(1L));
        Assert.assertEquals(10, gameState.getSharePrice(2L));
        Set<CompetitorAccount> competitorAccounts = gameState.getCompetitorAccounts();
        for (CompetitorAccount competitorAccount : competitorAccounts) {
            Assert.assertEquals(competitorAccount.getMoveOrder() == 1 ? 90 : 0, competitorAccount.getCash());
            Assert.assertEquals(competitorAccount.getMoveOrder() == 1 ? 450 : 350, competitorAccount.getTotal());
            Assert.assertEquals(competitorAccount.getMoveOrder() == 1 ? 1 : 0, competitorAccount.getShareQuantity(2L));
        }
    }

    @Test(dataProvider = "dataForHalfCompensation")
    public void testHalfCompensation(GameState gameState) throws Exception {
        gameState.setSharePrice(1L, ArithmeticOperation.DIVISION, 2);
        gameState.setSharePrice(2L, ArithmeticOperation.MULTIPLICATION, 2);
        Assert.assertEquals(10, gameState.getSharePrice(1L));
        Assert.assertEquals(200, gameState.getSharePrice(2L));
        Set<CompetitorAccount> competitorAccounts = gameState.getCompetitorAccounts();
        for (CompetitorAccount competitorAccount : competitorAccounts) {
            Assert.assertEquals(competitorAccount.getMoveOrder() == 1 ? 0 : 0, competitorAccount.getCash());
            Assert.assertEquals(competitorAccount.getMoveOrder() == 1 ? 410 : 400, competitorAccount.getTotal());
        }
    }

    public void testCommit() throws Exception {
        GameState gameState = generateGameState(10, 250, new int[]{100, 100, 100, 100},
                new int[][]{{1, 1, 1, 1}, {1, 1, 1, 1}}, new int[]{0, 0}, 2, 1, 1, new int[0]);
        Assert.assertEquals(1, gameState.getCurrentMoveNumber());
        Assert.assertEquals(1, gameState.getCurrentMoveOrder());
        gameState.commit();
        Assert.assertEquals(1, gameState.getCurrentMoveNumber());
        Assert.assertEquals(2, gameState.getCurrentMoveOrder());
        gameState.commit();
        Assert.assertEquals(2, gameState.getCurrentMoveNumber());
        Assert.assertEquals(1, gameState.getCurrentMoveOrder());
        gameState.commit();
        Assert.assertEquals(2, gameState.getCurrentMoveNumber());
        Assert.assertEquals(2, gameState.getCurrentMoveOrder());
        gameState.commit();
        Assert.assertEquals(2, gameState.getCurrentMoveNumber());
        Assert.assertEquals(2, gameState.getCurrentMoveOrder());
        Assert.assertTrue(gameState.isFinished());
        gameState.commit();
        Assert.assertEquals(2, gameState.getCurrentMoveNumber());
        Assert.assertEquals(2, gameState.getCurrentMoveOrder());
        Assert.assertTrue(gameState.isFinished());
    }

    @Test(dataProvider = "data4testRepurchase")
    public void testRepurchase(GameState gameState, int repurchaseCompetitorMoveOrder, int[] sharesQuantity, int cashValue)
            throws Exception {
        gameState.setSharePrice(1L, ArithmeticOperation.SUBTRACTION, 60);
        List<SharePrice> sharePrices = gameState.getSharePricesOrderedByRedemptionSumAndOldPrice();
        int redemptionSum = 0;
        for (SharePrice sharePrice : sharePrices) {
            if (sharePrice.getShareId().equals(1L)) {
                redemptionSum = sharePrice.getRedemptionSum();
            }
        }
        int repurchasedSharesQuantity = gameState.repurchase(repurchaseCompetitorMoveOrder, redemptionSum, 1L);
        gameState.commit();
        CompetitorAccount competitorAccount = gameState.getCompetitorAccount(repurchaseCompetitorMoveOrder);
        Assert.assertEquals(cashValue, competitorAccount.getCash());
        Assert.assertEquals(sharesQuantity[0], repurchasedSharesQuantity);

        for (Long shareId : gameState.getShareIds()) {
            Assert.assertEquals(sharesQuantity[(int) (shareId - 1)], competitorAccount.getShareQuantity(shareId));
        }
    }

    @Test(dataProvider = "data4testGetSharePricesOrderedByRedemptionSumAndOldPrice")
    public void testGetSharePricesOrderedByRedemptionSumAndOldPrice(int[] oldSharePrices, int[] redemptionSums, long[] expectedShareIdsOrder)
            throws SharePriceAlreadyChangedException {
        List<SharePrice> sharePrices = new ArrayList<SharePrice>();
        for (int i = 0; i < oldSharePrices.length; i++) {
            SharePrice sharePrice = new SharePriceImpl((long) i + 1, oldSharePrices[i]);
            sharePrice.setValue(10);
            sharePrice.setRedemptionSum(redemptionSums[i]);
            sharePrices.add(sharePrice);
        }

        GameState gameState = new GameStateImpl(8, 1, 1, sharePrices, new ArrayList<CompetitorAccount>(0), new PriceScaleImpl(250, 10, false));
        List<SharePrice> ordered = gameState.getSharePricesOrderedByRedemptionSumAndOldPrice();
        for (int i = 0; i < expectedShareIdsOrder.length; i++) {
            long expectedShareId = expectedShareIdsOrder[i];
            Assert.assertEquals(new Long(expectedShareId), ordered.get(i).getShareId());
        }
    }

    @DataProvider
    private Object[][] data4testGetSharePricesOrderedByRedemptionSumAndOldPrice() {
        return new Object[][] {
                {new int[]{10, 10, 10, 200}, new int[]{30, 20, 10, 0}, new long[]{4L, 3L, 2L, 1L}},
                {new int[]{20, 10, 10, 200}, new int[]{20, 20, 10, 0}, new long[]{4L, 3L, 2L, 1L}},
                {new int[]{10, 20, 10, 200}, new int[]{20, 20, 10, 0}, new long[]{4L, 3L, 1L, 2L}},
                {new int[]{10, 10, 20, 200}, new int[]{10, 20, 20, 0}, new long[]{4L, 1L, 2L, 3L}},
        };
    }

    /*@Test(dataProvider = "data4testCompetitorBankrupting")
    public void testCompetitorBankrupting(GameState gameState, int[] outCompetitors, boolean isGameFinished, int expectedMoveOrder)
            throws Exception {
        gameState.setSharePrice(1L, ArithmeticOperation.SUBTRACTION, 130);
        gameState.commit();
        if (outCompetitors.length == 0) {
            for (int i = 1; i <= gameState.getCompetitorsQuantity(); i++) {
                Assert.assertFalse(gameState.getCompetitorAccount(i).isOut());
            }
        } else {
            for (int outCompetitor : outCompetitors) {
                Assert.assertTrue(gameState.getCompetitorAccount(outCompetitor).getTotal() < 0);
                Assert.assertTrue(gameState.getCompetitorAccount(outCompetitor).isOut());
            }
        }
        Assert.assertEquals(isGameFinished, gameState.isFinished());
        Assert.assertEquals(expectedMoveOrder, gameState.getCurrentMoveOrder());
    }*/

    @Test(expectedExceptions = GameIsFinishedException.class, dataProvider = "data4testGameIsFinished")
    public void testGameIsFinished(GameStateCallback callback) throws Exception {
        GameState gameState = generateGameState(10, 250, new int[]{100, 100, 100, 100},
                new int[][]{{1, 1, 1, 1}, {1, 1, 1, 1}}, new int[]{0, 0}, 1, 1, 1, new int[0]);
        Assert.assertEquals(1, gameState.getCurrentMoveNumber());
        Assert.assertEquals(1, gameState.getCurrentMoveOrder());
        gameState.commit();
        Assert.assertEquals(1, gameState.getCurrentMoveNumber());
        Assert.assertEquals(2, gameState.getCurrentMoveOrder());
        gameState.commit();
        callback.exec(gameState);
    }

    @DataProvider
    private Object[][] data4testRepurchase() {
        return new Object[][]{
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1}, {1, 1, 0, 0}}, new int[]{0, 0}, 8, 1, 1, new int[0]), 2, new int[]{0, 1, 0, 0}, 0},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1}, {1, 1, 0, 0}, {1, 1, 1, 1}}, new int[]{0, 0, 0}, 8, 1, 1, new int[0]), 2, new int[]{0, 1, 0, 0}, 0},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1},  {1, 1, 1, 1}, {1, 1, 0, 0}}, new int[]{0, 0, 0}, 8, 1, 1, new int[0]), 3, new int[]{0, 1, 0, 0}, 0},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1},  {10, 1, 0, 0}}, new int[]{0, 100}, 8, 1, 1, new int[0]), 2, new int[]{2, 1, 0, 0}, 20},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1},  {10, 1, 0, 0}}, new int[]{0, 80}, 8, 1, 1, new int[0]), 2, new int[]{2, 1, 0, 0}, 0},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1},  {0, 1, 0, 0}}, new int[]{0, 80}, 8, 1, 1, new int[0]), 2, new int[]{0, 1, 0, 0}, 80},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1},  {10, 1, 0, 0}}, new int[]{0, 500}, 8, 1, 1, new int[0]), 2, new int[]{10, 1, 0, 0}, 100},
        };
    }

    @DataProvider
    private Object[][] data4testCompetitorBankrupting() {
        return new Object[][]{
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}}, new int[]{0, 0}, 1, 1, 1, new int[0]), new int[]{2}, true, 1},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}, {1, 1, 1, 1}}, new int[]{0, 0, 0}, 1, 1, 1, new int[0]), new int[]{2}, false, 3},
                {generateGameState(10, 250, new int[]{30, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1},  {1, 1, 1, 1}, {1, 0, 0, 0}}, new int[]{0, 0, 0}, 1, 1, 1, new int[0]), new int[0], false, 2}
        };
    }

    private interface GameStateCallback {
        void exec(GameState gameState) throws Exception;
    }

    @DataProvider
    private Object[][] data4testGameIsFinished() {
        return new Object[][]{
                {new GameStateCallback() {
                    public void exec(GameState gameState) throws Exception {
                        gameState.buySellShare(1L, -1);
                    }
                }},
                {new GameStateCallback() {
                    public void exec(GameState gameState) throws Exception {
                        gameState.setSharePrice(1L, ArithmeticOperation.ADDITION, 100);
                    }
                }}
        };
    }

    @DataProvider
    private Object[][] dataForTests() {
        return new Object[][]{
                {generateGameState(10, 250, new int[]{100, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}},
                        new int[]{0, 0, 0, 0, 0, 0}, 8, 1, 1, new int[0])}
        };
    }

    @DataProvider
    private Object[][] dataForHalfCompensation() {
        return new Object[][]{
                {generateGameState(10, 250, new int[]{10, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1}, {1, 1, 1, 1}},
                        new int[]{0, 0}, 8, 1, 1, new int[0])}
        };
    }
}
