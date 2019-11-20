package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dto.game.CompetitorMoveDto;
import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.GameDto;
import com.stockholdergame.server.dto.game.GameInitiationDto;
import com.stockholdergame.server.dto.game.GameStatusDto;
import com.stockholdergame.server.dto.game.MoveDto;
import com.stockholdergame.server.dto.game.variant.GameVariantDto;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.gamebot.GameBotBusinessDelegate;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.variant.GameVariantInfo;
import com.stockholdergame.server.services.game.GameService;
import com.stockholdergame.server.services.game.GameVariantService;
import com.stockholdergame.server.services.game.PlayGameService;
import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.util.game.GameStateCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 */
@Component
public class GameBotBusinessDelegateImpl implements GameBotBusinessDelegate {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayGameService playGameService;

    @Autowired
    private GameVariantService gameVariantService;

    @Override
    public List<Long> findVariantsWithoutOffers() {
        return gameDao.findVariantsWithoutOffers();
    }

    @Override
    public GameStatusDto initiateGame(GameInitiationDto gameInitiationDto, Long botId) {
        return gameService.initiateGame(gameInitiationDto, botId);
    }

    @Override
    public int countUserInitiatedGamesByMethod(Long userId, GameInitiationMethod initiationMethod) {
        return gameDao.countUserInitiatedGamesByMethod(userId, initiationMethod);
    }

    @Override
    public List<GamerAccount> findBots() {
        return gamerAccountDao.findBots();
    }

    @Override
    @Transactional
    public GameState getGame(Long gameId, Long botId) {
        Game game = gameDao.findGameByIdAndUserId(gameId, botId);
        GameState gameState = null;
        if (game != null && GameStatus.RUNNING.equals(game.getGameStatus())) {
            gameState = GameStateCreator.createGameState(game);
            /*for (CompetitorAccount competitorAccount : gameState.getCompetitorAccounts()) {
                CompetitorAccountExtraData extraData = competitorAccount.getExtraData();
                if (extraData.getGamerId().equals(botId)) {
                    continue;
                }
                extraData.setAvailableCardsMap(new HashMap<Long, Long>());
            }*/ // todo - resolve problem with price operations. This code should be uncommented.
        }
        return gameState;
    }

    @Override
    public GameVariantDto getGameVariant(Long gameVariantId) {
        return gameVariantService.getGameVariantById(gameVariantId);
    }

    @Override
    public GameVariantInfo getGameVariantInfo(Long gameVariantId) {
        return gameVariantService.getGameVariantInfo(gameVariantId);
    }

    @Override
    public void doMove(DoMoveDto doMoveDto, Long botId) {
        String botName = gamerAccountDao.findByPrimaryKey(botId).getUserName();
        UserInfo userInfo = new UserInfo(botId, botName, null, LocaleRegistry.getDefaultLocale(), null, null);
        playGameService.doMove(doMoveDto, userInfo);
    }

    @Override
    @Transactional
    public Set<MoveDto> loadLastMoves(int currentMoveNumber, int currentMoveOrder, Long gameId, Long botId) {
        Set<MoveDto> lastMoves = new TreeSet<>();
        Game game = gameDao.findGameByIdAndUserId(gameId, botId);
        if (game != null) {
            GameDto gameDto = DtoMapper.map(game, GameDto.class);
            Set<MoveDto> moves = gameDto.getMoves();
            for (MoveDto move : moves) {
                if (move.getMoveNumber() >= currentMoveNumber) {
                    if (lastMoves.isEmpty()) {
                        Set<CompetitorMoveDto> competitorMovesForRemove = new TreeSet<>();
                        Set<CompetitorMoveDto> competitorMoves = move.getCompetitorMoves();
                        for (CompetitorMoveDto competitorMove : competitorMoves) {
                            if (competitorMove.getMoveOrder() < currentMoveOrder) {
                                competitorMovesForRemove.add(competitorMove);
                            }
                        }
                        move.getCompetitorMoves().removeAll(competitorMovesForRemove);
                    }
                    lastMoves.add(move);
                }
            }
        }
        return lastMoves;
    }

    @Override
    public List<Long> findPlayedGames(Long botId) {
        return gameDao.findGameIdsByUserIdAndStatus(botId, Collections.singletonList(GameStatus.RUNNING));
    }
}
