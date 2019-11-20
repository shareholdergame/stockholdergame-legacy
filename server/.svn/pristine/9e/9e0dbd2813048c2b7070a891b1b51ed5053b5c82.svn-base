package com.stockholdergame.server.services.game.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dao.InvitationDao;
import com.stockholdergame.server.dto.game.GameInitiationDto;
import com.stockholdergame.server.dto.game.GameStatusDto;
import com.stockholdergame.server.dto.game.event.UserNotification;
import com.stockholdergame.server.dto.game.variant.CardGroupDto;
import com.stockholdergame.server.dto.game.variant.GameShareDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.Competitor;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.variant.GameVariant;
import com.stockholdergame.server.model.game.variant.Rounding;
import com.stockholdergame.server.services.game.GameVariantService;
import com.stockholdergame.server.services.messaging.MessagingService;
import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import org.dozer.DozerBeanMapper;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

/**
 * @author Alexander Savin
 *         Date: 5.10.2010 22.20.27
 */
@Test
public class GameServiceTest {

    private GameServiceImpl gameService;

    private GameVariantService gameVariantService;

    private MessagingService messagingService;

    private GameDao gameDao;

    private InvitationDao invitationDao;

    private GamerAccountDao gamerAccountDao;

    private GameServiceDataProvider dataProvider = new GameServiceDataProvider();

    private UserInfo user1 = new UserInfo(1L, "user1", "user1@email.com", Locale.getDefault(), AccountStatus.ACTIVE, "");

    private UserInfo user2 = new UserInfo(2L, "user2", "user2@email.com", Locale.getDefault(), AccountStatus.ACTIVE, "");

    private UserInfo currentUser = user1;

    @BeforeMethod
    public void setUp() {
        gameService = new GameServiceImpl() {
            @Override
            protected UserInfo getCurrentUser() {
                return currentUser;
            }
        };
        gameDao = createMock(GameDao.class);
        invitationDao = createMock(InvitationDao.class);
        gamerAccountDao = createMock(GamerAccountDao.class);
        gameVariantService = createMock(GameVariantService.class);
        messagingService = createMock(MessagingService.class);

        gameService.setGameDao(gameDao);
        gameService.setInvitationDao(invitationDao);
        gameService.setGamerAccountDao(gamerAccountDao);
        gameService.setGameVariantService(gameVariantService);
        gameService.setMessagingService(messagingService);

        DtoMapper.getInstance().setMapper(new DozerBeanMapper(CollectionsUtil.newList("dto-mapping.xml")));
    }

    @DataProvider
    private Object[][] getGameVariants() {
        Object[][] data = dataProvider.createGameVariantData();
        return new Object[][] {
            {dataProvider.createGameVariants(), 1, data},
        };
    }

    @DataProvider
    private Object[][] getGameWithMoves() {
        return new Object[][] {
                {dataProvider.createGameData(), dataProvider.createMoveData()}
        };
    }

    @Test(dataProvider = "getGameVariants")
    public void testGetGameVariants(List<GameVariant> list, int size, Object[][] data) {
        expect(gameVariantService.getGameVariants()).andReturn(list);

        replay(gameVariantService);
        List<GameVariantDto> dtos = gameService.getGameVariants();
        assertEquals(dtos.size(), size);
        for (int i = 0; i < size; i++) {
            GameVariantDto dto = dtos.get(i);
            assertEquals(dto.getId(), data[i][0]);
            assertEquals(dto.getName(), data[i][1]);
            assertEquals(dto.getGamerInitialCash(), data[i][2]);
            assertEquals(dto.getMaxGamersQuantity(), data[i][3]);
            assertEquals(dto.getMovesQuantity(), data[i][4]);
            assertEquals(dto.getRounding(), ((Rounding) data[i][5]).name());
            assertEquals(dto.getPriceScale().getMaxValue(), data[i][6]);
            assertEquals(dto.getPriceScale().getScaleSpacing(), data[i][7]);
            assertEquals(dto.getShares().size(), ((Object[][]) data[i][8]).length);
            int j = 0;
            for (GameShareDto gameShareDto : dto.getShares()) {
                assertNotNull(gameShareDto.getId());
                assertNotNull(gameShareDto.getInitPrice());
                assertNotNull(gameShareDto.getInitQuantity());
                assertNotNull(gameShareDto.getColor());
                //assertEquals(gameShareDto.getId(), ((Object[][]) data[i][8])[j][0]);
                //assertEquals(gameShareDto.getInitPrice(), ((Object[][]) data[i][8])[j][1]);
                //assertEquals(gameShareDto.getInitQuantity(), ((Object[][]) data[i][8])[j][2]);
                //assertEquals(gameShareDto.getColor(), ((Color)((Object[][]) data[i][8])[j][3]).name());
                j++;
            }
            int k = 0;
            for (CardGroupDto cardGroupDto : dto.getCardGroups()) {
                assertNotNull(cardGroupDto.getGamerCardQuantity());
                assertNotNull(cardGroupDto.getGroupName());
                k++;
            }
        }
        verify(gameVariantService);
    }

    public void testInitiateGame() {
        GameInitiationDto initiationDto = new GameInitiationDto();
        initiationDto.setGameVariantId(1L);
        initiationDto.setOffer(true);
        initiationDto.setRounding(Rounding.U.name());

        GamerAccount gamerAccount = new GamerAccount();
        gamerAccount.setIsBot(false);

        expect(gameVariantService.getGameVariantById(1L)).andReturn(DtoMapper.map(dataProvider.createGameVariant(),
            GameVariantDto.class));
        expect(gamerAccountDao.findByPrimaryKey(1L)).andReturn(gamerAccount);
        gameDao.create(EasyMock.<Game>anyObject());
        expect(gameDao.countUserInitiatedGamesByMethod(1L, GameInitiationMethod.GAME_OFFER)).andReturn(0);

        replay(gameVariantService, gameDao, gamerAccountDao);
        gameService.initiateGame(initiationDto);
        verify(gameVariantService, gameDao, gamerAccountDao);
    }

    @Test(enabled = false)
    public void testJoinToGame() {
        Game game = new Game();
        game.setId(1L);
        game.setGameVariantId(1L);
        game.setCompetitorsQuantity(2);
        game.setGameStatus(GameStatus.OPEN);

        Competitor competitor = new Competitor();
        competitor.setId(1L);
        competitor.setGamerId(-1000L);
        competitor.setGame(game);
        competitor.setInitiator(true);

        game.setCompetitors(CollectionsUtil.newSet(competitor));

        expect(gameDao.findByPrimaryKey(game.getId())).andReturn(game);
        expect(gameVariantService.getGameVariantById(1L)).andReturn(DtoMapper.map(dataProvider.createGameVariant(),
                GameVariantDto.class));
        gameDao.update(game);
        expect(invitationDao.findCreatedInvitationByGameIdAndInviteeName(EasyMock.anyLong(), EasyMock.<String>anyObject())).andReturn(null);
        messagingService.send(EasyMock.anyLong(), EasyMock.<UserNotification>anyObject());

        replay(gameDao, gameVariantService, messagingService);
        GameStatusDto gameStatusDto = gameService.joinToGame(game.getId());
        verify(gameDao, gameVariantService, messagingService);

        //assertNotNull(gameDto.getCompetitors());
        //assertEquals(gameDto.getCompetitors().size(), 2);
        //assertNotNull(gameDto.getCompetitors().iterator().next().getCompetitorCards());
        //assertEquals(gameDto.getCompetitors().iterator().next().getCompetitorCards().size(), 10);
        //assertEquals(gameDto.getGameVariantId(), new Long(1L));
    }

    public void testShuffleObjects() {
        List<Long> longs = CollectionsUtil.newList(1L, 2L, 3L, 4L, 5L, 6L, 6L, 7L, 1L);

        List<Long> shuffledLongs = CollectionsUtil.shuffleObjects(longs, 3);

        assertEquals(shuffledLongs.size(), longs.size());
    }

    private void switchUser() {
        currentUser = currentUser.equals(user1) ? user2 : user1;
    }
}
