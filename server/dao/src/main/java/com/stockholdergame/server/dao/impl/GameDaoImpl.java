package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.GameDao;
import com.stockholdergame.server.model.game.Competitor;
import com.stockholdergame.server.model.game.CompetitorMove;
import com.stockholdergame.server.model.game.CompetitorProjection;
import com.stockholdergame.server.model.game.Game;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameSeries;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.model.game.Move;
import com.stockholdergame.server.model.game.MoveStep;
import com.stockholdergame.server.model.game.RelatedGameProjection;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;

/**
 * @author Alexander Savin
 */
@Repository
public class GameDaoImpl extends BaseDao<Game, Long> implements GameDao {

    public List<Game> findGamesByGamerId(Long gamerId) {
        return findList("Game.findGamesByGamerId", gamerId);
    }

    public Game findGameByIdAndUserId(Long gameId, Long gamerId) {
        return findSingleObject("Game.findGameByIdAndUserId", gameId, gamerId);
    }

    public Game findGameByIdAndUserIdAndStatus(Long gameId, Long gamerId, GameStatus gameStatus) {
        return findSingleObject("Game.findGameByIdAndUserIdAndStatus", gameId, gamerId, gameStatus);
    }

    @SuppressWarnings("unchecked")
    public boolean isGameInitiatedByUser(Long gameId, Long gamerId) {
        String namedQuery = "Game.isGameInitiatedByUser";
        List<Long> ids = (List<Long>) findByNamedQuery(namedQuery, gameId, gamerId);
        if(ids.size() > 1) {
            throw new NonUniqueResultException(format(BaseDao.NON_UNIQUE_RESULT_MESSAGE, namedQuery, gameId, gamerId));
        } else {
            return !ids.isEmpty();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> countMyGamesByStatus(Long gamerId) {
        return (List<Object[]>) findByNamedQuery("Game.countMyGamesByStatus", gamerId);
    }

    public int countUserInitiatedGamesByMethod(Long userId, GameInitiationMethod initiationMethod) {
        return ((Long) findByNamedQuery("Game.countUserInitiatedGamesByMethod", userId, initiationMethod)
                .iterator().next()).intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CompetitorProjection> getPlayingUsers(Long gameVariantId, GameInitiationMethod gameInitiationMethod, int maxResults) {
        Query query = em.createNamedQuery("Game.findPlayingUsers");
        query.setFirstResult(0);
        query.setMaxResults(maxResults);
        query.setParameter(1, gameVariantId);
        query.setParameter(2, gameInitiationMethod);
        return query.getResultList();
    }

    @Override
    public List<Game> findExpiredGameOffers(Date date) {
        return findList("Game.findExpiredGameOffers", date);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Game> findLongDrawnGames(Date date, int offset, int limit) {
        Query query = em.createNamedQuery("Game.findLongDrawnGames");
        query.setParameter(1, date);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<Long> gameIds = query.getResultList();
        return gameIds.isEmpty() ? ListUtils.EMPTY_LIST : findList("Game.findGamesByIds", gameIds);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> findGameIdsByStatus(List<GameStatus> gameStatuses, int limit, int offset) {
        Query query = em.createNamedQuery("Game.findGameIdsByStatus");
        query.setParameter(1, gameStatuses);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> findVariantsWithoutOffers() {
        Query query = em.createNamedQuery("Game.findVariantsWithoutOffers");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> findGameIdsByUserIdAndStatus(Long botId, List<GameStatus> gameStatuses) {
        Query query = em.createNamedQuery("Game.findGameIdsByUserIdAndStatus");
        query.setParameter(1, botId);
        query.setParameter(2, gameStatuses);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countGamesInSeries(Long gameSeriesId) {
        List<Long> count = (List<Long>) findByNamedQuery("Game.countGamesInSeries", gameSeriesId);
        return count != null && !count.isEmpty() ? count.get(0).intValue() : 0;
    }

    @Override
    public void removeGameSeries(Long gameSeriesId) {
        Query deleteGameSeriesQuery = em.createQuery("DELETE FROM GameSeries gs WHERE gs.id = ?1");
        deleteGameSeriesQuery.setParameter(1, gameSeriesId).executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RelatedGameProjection> findRelatedGameIds(Long gameId, Long gameSeriesId) {
        Query query = em.createNamedQuery("Game.findRelatedGameIds");
        query.setParameter(1, gameId);
        query.setParameter(2, gameSeriesId);
        return (List<RelatedGameProjection>) query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> findCompletedGameSeries(int limit, int offset) {
        Query query = em.createNamedQuery("Game.findCompletedGameSeries");
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Override
    public GameSeries findGameSeriesById(Long gameSeriesId) {
        Query query = em.createNamedQuery("Game.findGameSeriesById");
        query.setParameter(1, gameSeriesId);
        return (GameSeries) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GameSeries> findOrphanGameSeries() {
        Query query = em.createNamedQuery("Game.findOrphanGameSeries");
        return query.getResultList();
    }

    @Override
    public void remove(Game game) {
        Set<Move> moves = game.getMoves();
        for (Move move : moves) {
            Set<CompetitorMove> competitorMoves = move.getCompetitorMoves();
            for (CompetitorMove competitorMove : competitorMoves) {
                List<MoveStep> moveSteps = competitorMove.getSteps();
                for (MoveStep moveStep : moveSteps) {
                    Query deleteCompensationsQuery = em.createQuery("DELETE FROM Compensation c WHERE c.step = ?1");
                    deleteCompensationsQuery.setParameter(1, moveStep).executeUpdate();

                    Query deleteSharePricesQuery = em.createQuery("DELETE FROM SharePrice sp WHERE sp.step = ?1");
                    deleteSharePricesQuery.setParameter(1, moveStep).executeUpdate();

                    Query deleteShareQuantityQuery = em.createQuery("DELETE FROM ShareQuantity sq WHERE sq.step = ?1");
                    deleteShareQuantityQuery.setParameter(1, moveStep).executeUpdate();

                    if (moveStep.getOriginalStep() != null) {
                        Query deleteMoveStepsQuery = em.createQuery("DELETE FROM MoveStep ms WHERE ms.id = ?1");
                        deleteMoveStepsQuery.setParameter(1, moveStep.getId()).executeUpdate();
                    }
                }
            }
        }
        for (Move move : moves) {
            Set<CompetitorMove> competitorMoves = move.getCompetitorMoves();
            for (CompetitorMove competitorMove : competitorMoves) {
                Query deleteMoveStepsQuery = em.createQuery("DELETE FROM MoveStep ms WHERE ms.competitorMove = ?1");
                deleteMoveStepsQuery.setParameter(1, competitorMove).executeUpdate();
            }
            Query deleteCompetitorMovesQuery = em.createQuery("DELETE FROM CompetitorMove cm WHERE cm.move = ?1");
            deleteCompetitorMovesQuery.setParameter(1, move).executeUpdate();
        }

        Query deleteMovesQuery = em.createQuery("DELETE FROM Move m WHERE m.game = ?1");
        deleteMovesQuery.setParameter(1, game).executeUpdate();

        Set<Competitor> competitors = game.getCompetitors();
        for (Competitor competitor : competitors) {
            Query deleteCompetitorCardsQuery = em.createQuery("DELETE FROM CompetitorCard cc WHERE cc.competitor = ?1");
            deleteCompetitorCardsQuery.setParameter(1, competitor).executeUpdate();
        }
        Query deleteCompetitorsQuery = em.createQuery("DELETE FROM Competitor c WHERE c.game = ?1");
        deleteCompetitorsQuery.setParameter(1, game).executeUpdate();

        Query deleteGame = em.createQuery("DELETE FROM Game g WHERE g.id = ?1");
        deleteGame.setParameter(1,  game.getId()).executeUpdate();
    }
}
