package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.game.CompetitorProjection;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameSeries;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.RelatedGameProjection;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 */
public interface GameDao extends GenericDao<Game, Long> {

    List<Game> findGamesByGamerId(Long gamerId);

    Game findGameByIdAndUserId(Long gameId, Long gamerId);

    Game findGameByIdAndUserIdAndStatus(Long gameId, Long gamerId, GameStatus gameStatus);

    boolean isGameInitiatedByUser(Long gameId, Long gamerId);

    List<Object[]> countMyGamesByStatus(Long gamerId);

    int countUserInitiatedGamesByMethod(Long userId, GameInitiationMethod initiationMethod);

    List<CompetitorProjection> getPlayingUsers(Long gameVariantId, GameInitiationMethod gameOffer, int maxResults);

    List<Game> findExpiredGameOffers(Date date);

    List<Game> findLongDrawnGames(Date date, int offset, int limit);

    List<Long> findGameIdsByStatus(List<GameStatus> gameStatuses, int limit, int offset);

    List<Long> findVariantsWithoutOffers();

    List<Long> findGameIdsByUserIdAndStatus(Long botId, List<GameStatus> gameStatuses);

    int countGamesInSeries(Long gameSeriesId);

    void removeGameSeries(Long gameSeriesId);

    List<RelatedGameProjection> findRelatedGameIds(Long gameId, Long gameSeriesId);

    List<Long> findCompletedGameSeries(int limit, int offset);

    GameSeries findGameSeriesById(Long gameSeriesId);

    List<GameSeries> findOrphanGameSeries();
}
