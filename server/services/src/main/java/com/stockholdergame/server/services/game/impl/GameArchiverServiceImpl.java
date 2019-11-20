package com.stockholdergame.server.services.game.impl;

import com.stockholdergame.server.dao.FinishedGameDao;
import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.dto.game.GameDto;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.model.game.Competitor;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameSeries;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.RelatedGameProjection;
import com.stockholdergame.server.model.game.archive.FinishedGame;
import com.stockholdergame.server.model.game.archive.FinishedGameCompetitor;
import com.stockholdergame.server.model.game.archive.FinishedGameCompetitorPk;
import com.stockholdergame.server.model.game.archive.FinishedGameSeries;
import com.stockholdergame.server.services.game.GameArchiverService;
import com.stockholdergame.server.services.game.InvitationService;
import com.stockholdergame.server.util.collections.Comparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.stockholdergame.server.util.AMFHelper.serializeToAmf;

@Service
public class GameArchiverServiceImpl implements GameArchiverService {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private FinishedGameDao finishedGameDao;

    @Override
    @Transactional
    public void archiveGame(Long gameSeriesId) {
        GameSeries gameSeries = gameDao.findGameSeriesById(gameSeriesId);
        if (gameSeries == null) {
            throw new ApplicationException("Game series not found " + gameSeriesId);
        }
        FinishedGameSeries finishedGameSeries = checkFinishedGameSeries(gameSeries.getId());

        Set<Game> games = gameSeries.getGames();
        Map<Game, byte[]> serializedGames = new HashMap<>();
        for (Game game : games) {
            GameDto gameDto = DtoMapper.map(game, GameDto.class);
            byte[] amfObject = serializeToAmf(gameDto);
            serializedGames.put(game, amfObject);
        }

        createFinishedGame(gameSeries, serializedGames, finishedGameSeries);
        for (Game game : games) {
            removeGameWithInvitations(game);
        }
        gameDao.removeGameSeries(gameSeriesId);
    }

    private FinishedGameSeries checkFinishedGameSeries(Long gameSeriesId) {
        return finishedGameDao.findGameSeriesByPrimaryKey(gameSeriesId);
    }

    private void removeGameWithInvitations(Game game) {
        invitationService.removeInvitations(game.getId());
        gameDao.remove(game);
    }

    @Override
    @Transactional
    public void removeGame(Long gameId) {
        Game game = gameDao.findByPrimaryKey(gameId);
        if (game == null) {
            throw new ApplicationException("Game not found " + gameId);
        }
        if (Comparer.in(game.getGameStatus(), GameStatus.CANCELLED, GameStatus.INTERRUPTED)) {
            Long gameSeriesId = game.getGameSeries().getId();
            removeGameWithInvitations(game);
            List<RelatedGameProjection> relatedGames = gameDao.findRelatedGameIds(gameId, gameSeriesId);
            if (relatedGames.size() == 0) {
                gameDao.removeGameSeries(gameSeriesId);
            }
        } else {
            throw new ApplicationException("removeGame error: invalid status of game: " + game.getGameStatus());
        }
    }

    @Override
    @Transactional
    public void removeOrphanGameSeries() {
        List<GameSeries> gameSeriesList = gameDao.findOrphanGameSeries();
        for (GameSeries gameSeries : gameSeriesList) {
            gameDao.removeGameSeries(gameSeries.getId());
        }
    }

    private void createFinishedGame(GameSeries gameSeries, Map<Game, byte[]> serializedGames,
                                    FinishedGameSeries existedFinishedGameSeries) {
        FinishedGameSeries finishedGameSeries;
        if (existedFinishedGameSeries != null) {
            finishedGameSeries = existedFinishedGameSeries;
        } else {
            finishedGameSeries = new FinishedGameSeries();
            finishedGameSeries.setId(gameSeries.getId());
            finishedGameSeries.setPlayWithBot(gameSeries.getPlayWithBot());
            finishedGameSeries.setCompetitorsQuantity(gameSeries.getCompetitorsQuantity());
            finishedGameSeries.setGameVariantId(gameSeries.getGameVariantId());
            finishedGameSeries.setCreatedTime(gameSeries.getCreatedTime());
            finishedGameSeries.setFinishedTime(gameSeries.getFinishedTime());
            finishedGameSeries.setStartedTime(gameSeries.getStartedTime());
            finishedGameSeries.setSwitchMoveOrder(gameSeries.getSwitchMoveOrder());
            finishedGameSeries.setRulesVersion(gameSeries.getRulesVersion());
            finishedGameSeries.setGames(new HashSet<FinishedGame>());
        }

        for (Map.Entry<Game, byte[]> entry : serializedGames.entrySet()) {
            Game game = entry.getKey();

            FinishedGame finishedGame = new FinishedGame();
            finishedGame.setId(game.getId());
            finishedGame.setRounding(game.getRounding());
            finishedGame.setLabel(game.getLabel());
            finishedGame.setGameVariantId(game.getGameVariantId());
            finishedGame.setCompetitorsQuantity(game.getCompetitorsQuantity());
            finishedGame.setCreatedTime(game.getCreatedTime());
            finishedGame.setStartedTime(game.getStartedTime());
            finishedGame.setFinishedTime(game.getFinishedTime());
            finishedGame.setGameLetter(game.getGameLetter());
            finishedGame.setRulesVersion(game.getRulesVersion());
            finishedGame.setGameObject(entry.getValue());

            finishedGame.setGameSeries(finishedGameSeries);
            finishedGameSeries.getGames().add(finishedGame);

            finishedGame.setCompetitors(new HashSet<FinishedGameCompetitor>());
            for(Competitor competitor : game.getCompetitors()) {
                FinishedGameCompetitor finishedGameCompetitor = new FinishedGameCompetitor();
                finishedGameCompetitor.setId(new FinishedGameCompetitorPk(game.getId(), competitor.getGamerId()));
                finishedGameCompetitor.setGame(finishedGame);
                //finishedGameCompetitor.setWinner(competitor.getWinner());
                finishedGameCompetitor.setOut(competitor.getOut());
                //finishedGameCompetitor.setTotalFunds(competitor.getTotalFunds());
                finishedGameCompetitor.setMoveOrder(competitor.getMoveOrder());
                finishedGame.getCompetitors().add(finishedGameCompetitor);
            }
        }

        finishedGameDao.createGameSeries(finishedGameSeries);
    }
}
