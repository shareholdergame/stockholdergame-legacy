package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.game.RelatedGameProjection;
import com.stockholdergame.server.model.game.archive.FinishedGame;
import com.stockholdergame.server.model.game.archive.FinishedGameSeries;

import java.util.Date;
import java.util.List;

public interface FinishedGameDao extends GenericDao<FinishedGame, Long> {

    FinishedGame findByIdAndUserId(Long gameId, Long userId);

    List<Long> findGamesOlderThan(Date date, int limit);

    int countMyGames(Long userId);

    List<RelatedGameProjection> findRelatedGameIds(Long gameId, Long gameSeriesId);

    FinishedGameSeries findGameSeriesByPrimaryKey(Long gameSeriesId);

    void createGameSeries(FinishedGameSeries finishedGameSeries);
}
