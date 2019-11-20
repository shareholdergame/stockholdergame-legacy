package com.stockholdergame.server.gamecore.builders;

import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.impl.GameStateDataProvider;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 3.7.12 18.48
 */
public class GameStateBuilderTest {

    @Test
    public void testGetGameState() throws Exception {
        GameStateBuilder builder = GameStateBuilder.newBuilder(8, 0, 2, 10, 250, false);
        for (long shareId : GameStateDataProvider.SHARE_IDS) {
            builder.addShare(shareId, 100);
        }
        for (int i = 1; i <= 2; i++) {
            CompetitorAccountBuilder accountBuilder = builder.addCompetitor(i, false, 0);
            for (long shareId : GameStateDataProvider.SHARE_IDS) {
                accountBuilder.addShareQuantity(shareId, 1);
            }
            accountBuilder.finishBuilding();
        }
        GameState gameState = builder.getGameState();

        Assert.assertNotNull(gameState);
        Assert.assertEquals(1, gameState.getCurrentMoveNumber());
        Assert.assertEquals(1, gameState.getCurrentMoveOrder());
        Assert.assertEquals(8, gameState.getMovesQuantity());
        for (long shareId : GameStateDataProvider.SHARE_IDS) {
            Assert.assertEquals(100, gameState.getSharePrice(shareId));
        }
        for (int i = 1; i <= 2; i++) {
            Assert.assertEquals(i, gameState.getCompetitorAccount(i).getMoveOrder());
            Assert.assertEquals(0, gameState.getCompetitorAccount(i).getCash());
            Assert.assertFalse(gameState.getCompetitorAccount(i).isOut());
            for (long shareId : GameStateDataProvider.SHARE_IDS) {
                Assert.assertEquals(1, gameState.getCompetitorAccount(i).getShareQuantity(shareId));
                Assert.assertEquals(0, gameState.getCompetitorAccount(i).getLockedShareQuantity(shareId));
                Assert.assertNull(gameState.getCompetitorAccount(i).getCompensation(shareId));
            }
        }

    }
}
