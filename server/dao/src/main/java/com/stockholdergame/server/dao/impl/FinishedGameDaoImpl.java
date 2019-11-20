package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.FinishedGameDao;
import com.stockholdergame.server.model.game.RelatedGameProjection;
import com.stockholdergame.server.model.game.archive.FinishedGame;
import com.stockholdergame.server.model.game.archive.FinishedGameSeries;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class FinishedGameDaoImpl extends BaseDao<FinishedGame, Long> implements FinishedGameDao {

    public FinishedGame findByIdAndUserId(Long gameId, Long userId) {
        return findSingleObject("FinishedGame.findByIdAndUserId", gameId, userId);
    }

    @SuppressWarnings("unchecked")
    public List<Long> findGamesOlderThan(Date date, int limit) {
        Query query = em.createNamedQuery("FinishedGame.findGamesOlderThan");
        query.setParameter(1, date);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public int countMyGames(Long userId) {
        return ((Long) findByNamedQuery("FinishedGame.countMyGames", userId).iterator().next()).intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RelatedGameProjection> findRelatedGameIds(Long gameId, Long gameSeriesId) {
        Query query = em.createNamedQuery("FinishedGame.findRelatedGameIds");
        query.setParameter(1, gameId);
        query.setParameter(2, gameSeriesId);
        return (List<RelatedGameProjection>) query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public FinishedGameSeries findGameSeriesByPrimaryKey(Long gameSeriesId) {
        Query query = em.createNamedQuery("FinishedGame.findGameSeriesByPrimaryKey");
        query.setParameter(1, gameSeriesId);
        List<FinishedGameSeries> finishedGameSeries = query.getResultList();
        if (finishedGameSeries != null && !finishedGameSeries.isEmpty()) {
            return finishedGameSeries.iterator().next();
        } else {
            return null;
        }
    }

    @Override
    public void createGameSeries(FinishedGameSeries finishedGameSeries) {
        em.persist(finishedGameSeries);
    }
}
