package com.stockholdergame.server.dao.impl;

import com.stockholdergame.server.dao.GameMapperDao;
import com.stockholdergame.server.dto.game.CurrentTurnDto;
import com.stockholdergame.server.dto.game.GameVariantSummary;
import com.stockholdergame.server.dto.game.lite.CompetitorLite;
import com.stockholdergame.server.dto.game.lite.GameLite;
import com.stockholdergame.server.dto.game.lite.GamesList;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameStatus;
import com.stockholdergame.server.util.collections.MapBuilder;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class GameMapperDaoImpl extends BaseMapperDao implements GameMapperDao {

    @SuppressWarnings("unchecked")
    public GamesList findGamesByParameters(Long gamerId,
                                           GameStatus gameStatus,
                                           GameInitiationMethod initiationMethod,
                                           boolean isInitiator,
                                           boolean isNotInitiator,
                                           Long gameVariantId,
                                           String userName,
                                           int offset,
                                           int limit,
                                           boolean smallAvatar,
                                           String rulesVersion,
                                           Integer playersNumber) {
        Map<String, Object> params = createParametersMap(gamerId, gameStatus, initiationMethod, isInitiator, isNotInitiator, gameVariantId,
                userName, rulesVersion, playersNumber);

        Integer totalCount = (Integer) getSqlSession().selectOne("Game.countGamesByParameters", params);

        RowBounds rb = new RowBounds(offset, limit);
        List<GameLite> games = getSqlSession().selectList("Game.findGamesByParameters", params, rb);
        Map<Long, GameLite> gamesMap = buildMap(games);

        if (!gamesMap.isEmpty()) {
            Map<String, Object> params1 = new MapBuilder<String, Object>()
                    .append("gameIds", gamesMap.keySet())
                    .append("gameStatus", gameStatus != null ? gameStatus.ordinal() : null)
                    .append("smallAvatar", smallAvatar)
                    .toHashMap();
            List<CompetitorLite> children = getSqlSession().selectList("Game.findGameCompetitors", params1);
            for (CompetitorLite competitorLite : children) {
                GameLite game = gamesMap.get(competitorLite.getGameId());
                if (game.getCompetitors() == null) {
                    game.setCompetitors(new HashSet<CompetitorLite>());
                }
                game.getCompetitors().add(competitorLite);
            }
            if (GameStatus.RUNNING.equals(gameStatus)) {
                List<GameLite> shares = getSqlSession().selectList("Game.findGameShares", params1);
                for (GameLite shareList : shares) {
                    GameLite game = gamesMap.get(shareList.getId());
                    game.setLastMoveNumber(shareList.getLastMoveNumber());
                    game.setLastMoveOrder(shareList.getLastMoveOrder());
                    game.setLastMoveTime(shareList.getLastMoveTime());
                    game.setShares(shareList.getShares());
                }
            }
        }

        return new GamesList(totalCount, games);
    }

    private Map<String, Object> createParametersMap(Long gamerId,
                                                    GameStatus gameStatus,
                                                    GameInitiationMethod initiationMethod,
                                                    boolean isInitiator,
                                                    boolean isNotInitiator,
                                                    Long gameVariantId,
                                                    String userName,
                                                    String rulesVersion,
                                                    Integer playersNumber) {
        return new MapBuilder<String, Object>()
                    .append("gamerId", gamerId)
                    .append("initiationMethod", initiationMethod != null ? initiationMethod.ordinal() : null)
                    .append("isInitiator", isInitiator)
                    .append("isNotInitiator", isNotInitiator)
                    .append("gameVariantId", gameVariantId)
                    .append("userName", userName == null ? null : userName + "%")
                    .append("gameStatus", gameStatus != null ? gameStatus.ordinal() : null)
                    .append("rulesVersion", rulesVersion)
                    .append("playersNumber", playersNumber)
                    .toHashMap();
    }

    @Override
    public int countGamesByParameters(Long gamerId, GameStatus gameStatus, GameInitiationMethod initiationMethod, boolean isInitiator,
                                      boolean isNotInitiator, Long gameVariantId,
                                      String userName, String rulesVersion, Integer playersNumber) {
        Map<String, Object> params = createParametersMap(gamerId, gameStatus, initiationMethod, isInitiator, isNotInitiator, gameVariantId,
                userName, rulesVersion, playersNumber);

        return (Integer) getSqlSession().selectOne("Game.countGamesByParameters", params);
    }

    private Map<Long, GameLite> buildMap(List<GameLite> games) {
        Map<Long, GameLite> m = new HashMap<Long, GameLite>();
        for (GameLite game : games) {
            m.put(game.getId(), game);
        }
        return  m;
    }

    @SuppressWarnings("unchecked")
    public List<GameVariantSummary> countGamesByVariant(Long gamerId) {
        Map<String, Object> params = new MapBuilder<String, Object>()
                .append("gamerId", gamerId)
                .toHashMap();

        return getSqlSession().selectList("Game.countGamesByVariant", params);
    }

    @Override
    public List<CurrentTurnDto> getCurrentTurns() {
        return getSqlSession().selectList("Game.getCurrentTurns");
    }
}
