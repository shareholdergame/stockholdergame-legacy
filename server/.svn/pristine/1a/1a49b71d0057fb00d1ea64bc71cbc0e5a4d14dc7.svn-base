package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.model.BuySellAction;
import com.stockholdergame.server.gamecore.model.MoveData;
import com.stockholdergame.server.gamecore.model.PriceChangeAction;
import com.stockholdergame.server.gamecore.model.math.ArithmeticOperation;
import com.stockholdergame.server.gamecore.model.result.MoveResult;
import junit.framework.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.TreeSet;

import static com.stockholdergame.server.gamecore.impl.GameStateDataProvider.generateGameState;

/**
 * @author Alexander Savin
 *         Date: 24.6.12 22.56
 */
@Test
public class MovePerformerTest {

    @Test(dataProvider = "data4testDoMove")
    public void testDoMove(MoveData moveData, GameState gameState, int expectedMoveNumber, int expectedMoveOrder)
            throws Exception {
        MovePerformerImpl movePerformer = new MovePerformerImpl();
        MoveResult moveResult = movePerformer.doMove(moveData, gameState);
        Assert.assertNotNull(moveResult);
        Assert.assertEquals(expectedMoveNumber, gameState.getCurrentMoveNumber());
        Assert.assertEquals(expectedMoveOrder, gameState.getCurrentMoveOrder());
    }

    @DataProvider
    private Object[][] data4testDoMove() {
        return new Object[][]{
                {generateMoveData(new int[] {0, -1, 1, 0}, new ArithmeticOperation[] {null, ArithmeticOperation.SUBTRACTION, ArithmeticOperation.ADDITION, null},
                        new int[] {0, 50, 100, 0}, new int[] {0, 4, -1, 0}),
                        generateGameState(10, 250, new int[]{100, 100, 100, 100},
                        new int[][]{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}},
                        new int[]{0, 0, 0, 0, 0, 0}, 8, 1, 1, new int[0]), 1, 2}
        };
    }

    private Object generateMoveData(int[] firstBuySellActions, ArithmeticOperation[] operations, int[] operands, int[] lastBuySellActions) {
        Set<BuySellAction> firstBuySellActionsSet = new TreeSet<BuySellAction>();
        Set<PriceChangeAction> priceChangeActions = new TreeSet<PriceChangeAction>();
        Set<BuySellAction> lastBuySellActionsSet = new TreeSet<BuySellAction>();
        int i = 0;
        for (long shareId : GameStateDataProvider.SHARE_IDS) {
            firstBuySellActionsSet.add(new BuySellAction(shareId, firstBuySellActions[i]));
            priceChangeActions.add(new PriceChangeAction(shareId, operations[i], operands[i]));
            lastBuySellActionsSet.add(new BuySellAction(shareId, lastBuySellActions[i]));
            i++;
        }

        return new MoveData(firstBuySellActionsSet, priceChangeActions, lastBuySellActionsSet);
    }

    /*public void testCompetitorBankrupting() throws Exception {
        GameState gameState = generateGameState(10, 250, new int[]{30, 100, 100, 100},
                new int[][]{{1, 1, 1, 1}, {1, 0, 0, 0}}, new int[]{0, 0}, 1, 1, 1, new int[0]);
        int[] outCompetitors = new int[]{2};

        Set<PriceChangeAction> priceChangeActions = new HashSet<PriceChangeAction>();
        priceChangeActions.add(new PriceChangeAction(1L, ArithmeticOperation.SUBTRACTION, 60));
        MoveData moveData = new MoveData(null, priceChangeActions, null);
        MovePerformerImpl movePerformer = new MovePerformerImpl();
        MoveResult moveResult = movePerformer.doMove(moveData, gameState);

        Assert.assertNotNull(moveResult);
        Assert.assertNotNull(moveResult.getPriceChangeStepResult());
        Assert.assertNotNull(moveResult.getPriceChangeStepResult().getRepurchaseResult());
        Assert.assertEquals(moveResult.getPriceChangeStepResult().getRepurchaseResult().size(), 0);

        if (outCompetitors.length == 0) {
            for (int i = 1; i <= gameState.getCompetitorsQuantity(); i++) {
                Assert.assertFalse(gameState.getCompetitorAccount(i).isOut());
            }
        } else {
            for (int outCompetitor : outCompetitors) {
                Assert.assertEquals(0, gameState.getCompetitorAccount(outCompetitor).getTotal());
                Assert.assertTrue(gameState.getCompetitorAccount(outCompetitor).isOut());
            }
        }
        Assert.assertEquals(true, gameState.isFinished());
        Assert.assertEquals(1, gameState.getCurrentMoveOrder());
    }*/
}
