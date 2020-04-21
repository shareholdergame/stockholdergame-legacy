package com.stockholdergame.server.dao;

import com.stockholdergame.server.dao.util.IdentifierHelper;
import com.stockholdergame.server.dto.account.UserDto;
import com.stockholdergame.server.dto.account.UserFilterDto;
import com.stockholdergame.server.dto.game.GameVariantSummary;
import com.stockholdergame.server.dto.game.lite.GamesList;
import com.stockholdergame.server.model.account.ChatMessageProjection;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.Sex;
import com.stockholdergame.server.model.account.UserSessionLog;
import com.stockholdergame.server.model.game.Compensation;
import com.stockholdergame.server.model.game.Competitor;
import com.stockholdergame.server.model.game.CompetitorCard;
import com.stockholdergame.server.model.game.CompetitorMove;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameRulesVersion;
import com.stockholdergame.server.model.game.GameSeries;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.Invitation;
import com.stockholdergame.server.model.game.InvitationStatus;
import com.stockholdergame.server.model.game.Move;
import com.stockholdergame.server.model.game.MoveStep;
import com.stockholdergame.server.model.game.SharePrice;
import com.stockholdergame.server.model.game.SharePricePk;
import com.stockholdergame.server.model.game.ShareQuantity;
import com.stockholdergame.server.model.game.ShareQuantityPk;
import com.stockholdergame.server.model.game.TotalScoreProjection;
import com.stockholdergame.server.model.game.archive.FinishedGame;
import com.stockholdergame.server.model.game.archive.FinishedGameCompetitor;
import com.stockholdergame.server.model.game.archive.FinishedGameCompetitorPk;
import com.stockholdergame.server.model.game.variant.GameVariant;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static com.stockholdergame.server.model.account.AccountStatus.NEW;
import static com.stockholdergame.server.model.game.StepType.COMPENSATION_STEP;
import static com.stockholdergame.server.model.game.StepType.ZERO_STEP;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander Savin
 *         Date: 3.10.2010 23.50.40
 */
@Test(enabled = false)
@ContextConfiguration(locations = "classpath:dao-context-test.xml")
@Transactional
public class GenericDaoIntegrationTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private GameVariantDao gameVariantDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GameMapperDao gameMapperDao;

    @Autowired
    private InvitationDao invitationDao;

    @Autowired
    private UserSessionLogDao userSessionLogDao;

    @Autowired
    private UserMapperDao userMapperDao;

    @Autowired
    private FinishedGameDao finishedGameDao;

    @Autowired
    private ChatMessageDao chatMessageDao;

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private ScoreDao scoreDao;

    public static final long GAMER_ID = 1L;

    public void testCreateGamerAccount() {
        GamerAccount gamerAccount = new GamerAccount();
        gamerAccount.setId(IdentifierHelper.generateLongId());
        gamerAccount.setUserName("user");
        gamerAccount.setPassword("pwd");
        gamerAccount.setEmail("user@user.com");
        gamerAccount.setRegistrationDate(new Date());
        gamerAccount.setLocale(Locale.getDefault());
        gamerAccount.setSubtopicName("subtopic");
        gamerAccount.setStatus(NEW);

        gamerAccountDao.create(gamerAccount);
        assertNotNull(gamerAccount.getId());

        gamerAccount = gamerAccountDao.findByPrimaryKey(gamerAccount.getId());
        assertNotNull(gamerAccount);
    }

    @Test(enabled = false)
    public void testGameVariantDaoFindAllVariants() {
        List<GameVariant> gameVariants = gameVariantDao.findAllActiveVariants();
        assertNotNull(gameVariants);
        assertEquals(gameVariants.size(), 3);
    }

    @Test(enabled = false)
    public void testGameDaoFindOpenGamesByGamerId() {
        List<Game> games = gameDao.findGamesByGamerId(GAMER_ID);
        assertNotNull(games);
    }

    /*@Test
    public void testGameDaoFindAllActiveGamesByUserId() {
        List<Game> games = gameDao.findGamesByUserIdAndStatus(GAMER_ID, GameStatus.RUNNING);
        assertNotNull(games);
    } */

    @Rollback
    @Test(enabled = false)
    public void testGameDaoCreate() {
        Date current = new Date();

        Game game = prepareGameObject(current);

        gameDao.create(game);

        game = gameDao.findByPrimaryKey(game.getId());
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getCompetitors());
        assertEquals(game.getCompetitors().size(), 1);
        //assertNotNull(competitor.getId());

        game = gameDao.findGameByIdAndUserId(game.getId(), GAMER_ID);
        assertNotNull(game);

        List<Game> games = gameDao.findGamesByGamerId(GAMER_ID);
        assertFalse(games.isEmpty());
    }

    private Game prepareGameObject(Date current) {
        GameSeries gameSeries = new GameSeries();
        gameSeries.setId(IdentifierHelper.generateLongId());
        gameSeries.setCreatedTime(new Date());
        gameSeries.setGameVariantId(1L);
        gameSeries.setCompleted(false);
        gameSeries.setPlayWithBot(false);
        gameSeries.setSwitchMoveOrder(false);
        gameSeries.setRulesVersion(GameRulesVersion.RULES_1_3);
        gameSeries.setCompetitorsQuantity(2);

        Game game = new Game();
        game.setId(IdentifierHelper.generateLongId());
        game.setGameVariantId(1L);
        game.setMaxSharePrice(250);
        game.setSharePriceStep(10);
        game.setGameStatus(GameStatus.OPEN);
        game.setCreatedTime(current);
        game.setCompetitorsQuantity(2);
        game.setInitiationMethod(GameInitiationMethod.GAME_OFFER);
        game.setRulesVersion(GameRulesVersion.RULES_1_3);
        game.setGameLetter("A");
        game.setGameSeries(gameSeries);

        Competitor competitor = new Competitor();
        competitor.setInitiator(true);
        competitor.setJoinedTime(current);
        competitor.setGamerId(GAMER_ID);
        competitor.setGame(game);

        game.setCompetitors(CollectionsUtil.newSet(competitor));

        CompetitorCard competitorCard = new CompetitorCard();
        competitorCard.setCardId(1L);
        competitorCard.setCompetitor(competitor);

        competitor.setCompetitorCards(CollectionsUtil.newSet(competitorCard));

        Move move = new Move();
        move.setMoveNumber(0);
        move.setGame(game);
        game.setMoves(new HashSet<Move>(Arrays.asList(move)));

        CompetitorMove competitorMove = new CompetitorMove();
        competitorMove.setMoveOrder(1);
        competitorMove.setFinishedTime(current);
        competitorMove.setCompetitor(competitor);
        competitorMove.setMove(move);

        move.setCompetitorMoves(CollectionsUtil.newSet(competitorMove));

        MoveStep step = new MoveStep();
        step.setStepType(ZERO_STEP);
        step.setCompetitorMove(competitorMove);
        step.setCashValue(0);

        SharePrice sharePrice = new SharePrice();
        SharePricePk shpk = new SharePricePk();
        shpk.setShareId(1L);
        sharePrice.setId(shpk);
        sharePrice.setStep(step);
        sharePrice.setPrice(100);

        step.setSharePrices(CollectionsUtil.newSet(sharePrice));

        ShareQuantity shareQuantity = new ShareQuantity();
        ShareQuantityPk qpk = new ShareQuantityPk();
        qpk.setShareId(1L);
        shareQuantity.setId(qpk);
        shareQuantity.setQuantity(1);
        shareQuantity.setStep(step);

        step.setShareQuantities(CollectionsUtil.newSet(shareQuantity));

        MoveStep cmpsStep = new MoveStep();
        cmpsStep.setStepType(COMPENSATION_STEP);
        cmpsStep.setCompetitorMove(competitorMove);
        cmpsStep.setOriginalStep(step);
        cmpsStep.setCashValue(0);

        Compensation cc = new Compensation(null, 1L, 0, cmpsStep);
        cmpsStep.setCompensations(CollectionsUtil.newSet(cc));

        competitorMove.setSteps(CollectionsUtil.newList(step, cmpsStep));
        return game;
    }

    public void testCountGamesByVariant() {
        Game g = prepareGameObject(new Date());
        gameDao.create(g);

        Game g1 = prepareGameObject(new Date());
        g1.setGameStatus(GameStatus.RUNNING);
        gameDao.create(g1);

        List<GameVariantSummary> list = gameMapperDao.countGamesByVariant(2L);
        assertNotNull(list);
        //Assert.assertTrue(list.size() >= 2);
    }

    @DataProvider
    private Object[][] getUserParameters() {
        return new Object[][] {
                {"alex", Locale.ENGLISH, Sex.M, "Belarus", "Vitebsk", null, 0, 10, 1},
                {"alex", null, null, null, null, null, 0, 10, 1},
                {null, Locale.ENGLISH, null, null, null, null, 0, 10, 3},
                {null, Locale.FRANCE, null, null, null, null, 0, 10, 0},
                {null, null, Sex.M, null, null, null, 0, 10, 2},
                {null, null, Sex.F, null, null, null, 0, 10, 1},
                {null, null, null, "Belarus", null, null, 0, 10, 1},
                {null, null, null, "France", null, null, 0, 10, 0},
                {null, null, null, null, "Moscow", null, 0, 10, 1},
                {null, null, null, null, null, 1L, 0, 10, 2},
                {null, null, null, null, null, null, 0, 10, 3},
                {null, null, null, null, null, null, 0, 1, 1},
                {null, null, null, null, null, null, 1, 5, 2}
        };
    }

    public void testCountMyGamesByStatus() {
        List<Object[]> list = gameDao.countMyGamesByStatus(1L);
        assertNotNull(list);
    }

    public void testCountUserInvitations() {
        Long[] counts = invitationDao.countUserInvitations(1L);
        assertNotNull(counts);
    }

    @Test(dataProvider = "getDataForTestFindInvitationsByParameters", enabled = false)
    public void testFindInvitationsByParameters(Long inviterId, Long inviteeId, InvitationStatus... statuses) {
        List<Invitation> invitations = invitationDao.findByParameters(inviterId, inviteeId, statuses);
        assertNotNull(invitations);
    }

    @DataProvider
    private Object[][] getDataForTestFindInvitationsByParameters() {
        return new Object[][] {
            {1L, 2L, new InvitationStatus[] {InvitationStatus.CREATED, InvitationStatus.CANCELLED}}
        };
    }

    @Test(dataProvider = "getDataForTestFindGamesByParameters", enabled = false)
    public void testFindGamesByParameters(Long gamerId,
                                          GameStatus gameStatus,
                                          GameInitiationMethod method,
                                          boolean isInitiator) {
        GamesList games = gameMapperDao.findGamesByParameters(gamerId, gameStatus, method, isInitiator, false, null, null, 0, 5, false,
                null, null);
        assertNotNull(games);
        assertNotNull(games.getGames());
    }

    @DataProvider
    private Object[][] getDataForTestFindGamesByParameters() {
        return new Object[][]{
                {1L, GameStatus.OPEN, GameInitiationMethod.GAME_OFFER, false},
                {1L, GameStatus.OPEN, GameInitiationMethod.GAME_OFFER, true},
                {1L, GameStatus.OPEN, GameInitiationMethod.INVITATION, false},
                {1L, GameStatus.OPEN, GameInitiationMethod.INVITATION, true},
                {1L, GameStatus.RUNNING, null, false},
                {1L, GameStatus.FINISHED, null, false},
        };
    }

    public void testUserSessionLogDaoFindLastSession() {
        UserSessionLog userSessionLog = userSessionLogDao.findLastSession(1L);
        Assert.assertNotNull(userSessionLog);
    }

    @Test(dataProvider = "getDataForTestUserMapperDaoFindUsers", enabled = false)
    public void testUserMapperDaoFindUsers(Long currentUserId, String userName) {
        UserFilterDto userFilter = new UserFilterDto();
        userFilter.setUserNames(new String[] {userName});
        List<UserDto> users = userMapperDao.findUsers(currentUserId, userFilter);
        assertNotNull(users);
    }

    @DataProvider
    private Object[][] getDataForTestUserMapperDaoFindUsers() {
        return new Object[][] {
            {1L, null},
            {1L, "test"}
        };
    }

    public void testCreateFinishedGame() {
        Game game = prepareGameObject(new Date());

        FinishedGame finishedGame = new FinishedGame();
        finishedGame.setId(1L);
        finishedGame.setGameVariantId(game.getGameVariantId());
        finishedGame.setCompetitorsQuantity(game.getCompetitorsQuantity());
        finishedGame.setCreatedTime(game.getCreatedTime());
        finishedGame.setStartedTime(game.getStartedTime());
        finishedGame.setFinishedTime(game.getFinishedTime());
        finishedGame.setGameObject(new byte[] {1, 2, 3});

        finishedGame.setCompetitors(new HashSet<FinishedGameCompetitor>());
        for(Competitor competitor : game.getCompetitors()) {
            FinishedGameCompetitor finishedGameCompetitor = new FinishedGameCompetitor();
            finishedGameCompetitor.setId(new FinishedGameCompetitorPk(game.getId(), competitor.getGamerId()));
            finishedGameCompetitor.setGame(finishedGame);
            finishedGameCompetitor.setWinner(competitor.getWinner());
            finishedGameCompetitor.setOut(competitor.getOut());
            finishedGameCompetitor.setTotalFunds(competitor.getTotalFunds());
            finishedGame.getCompetitors().add(finishedGameCompetitor);
        }

        finishedGameDao.create(finishedGame);
    }

    @Test(enabled = false)
    public void testChatMessageDaoGetHistory() {
        List<ChatMessageProjection> userChats = chatMessageDao.findAllForLastDays("f358a0fd428631a81d4694583a330f1a", 7, "alex");
        assertNotNull(userChats);
    }

    @Test(enabled = false)
    public void testFriendsCount() {
        Long count = friendDao.countFriends(1L);
        assertNotNull(count);
    }

    @Test(enabled = false)
    public void testScorersCount() {
        int count = scoreDao.countScorers(1L, null, new String[] {GameRulesVersion.RULES_1_3});
        assertTrue(count > -1);
    }

    @Test(enabled = false)
    public void testTotalScoreCount() {
        TotalScoreProjection tsp = scoreDao.countTotalScore(1L, new String[] {GameRulesVersion.RULES_1_3});
        assertNotNull(tsp);
    }
}
