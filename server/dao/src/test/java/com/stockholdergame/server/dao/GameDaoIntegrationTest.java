package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameDataProvider;
import com.stockholdergame.server.model.game.Move;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 9.3.12 19.50
 */
@Test
@ContextConfiguration(locations = "classpath:dao-context-test.xml")
@Transactional
public class GameDaoIntegrationTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private GameDao gameDao;

    private Long gameId;

    @Rollback(false)
    @Test(enabled = false)
    public void testCreateGame() {
        Game game = GameDataProvider.FINISHED_GAME;
        gameDao.create(game);

        gameId = game.getId();
        Assert.assertNotNull(gameDao.findByPrimaryKey(gameId));
    }

    @Rollback(false)
    @Test(dependsOnMethods = "testCreateGame", enabled = false)
    public void testAddMove() {
        Game game = gameDao.findByPrimaryKey(gameId);

        Move m1 = GameDataProvider.createMove1(game);
        game.getMoves().add(m1);

        gameDao.update(game);
    }

    @Rollback(false)
    @Test(dependsOnMethods = "testAddMove", enabled = false)
    public void testRemoveGame() {
        Game game = gameDao.findByPrimaryKey(gameId);
        Assert.assertNotNull(game);

        gameDao.remove(game);
        Assert.assertNull(gameDao.findByPrimaryKey(gameId));
    }
}
