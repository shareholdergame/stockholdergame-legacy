package com.stockholdergame.server.dao;

import com.stockholdergame.server.dto.game.CurrentTurnDto;
import com.stockholdergame.server.dto.game.GameVariantSummary;
import com.stockholdergame.server.dto.game.lite.GamesList;
import com.stockholdergame.server.model.game.GameInitiationMethod;
import com.stockholdergame.server.model.game.GameStatus;

import java.util.List;

/**
 *
 */
public interface GameMapperDao {

    GamesList findGamesByParameters(Long gamerId,
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
                                    Integer playersNumber);

    int countGamesByParameters(Long gamerId,
                               GameStatus gameStatus,
                               GameInitiationMethod initiationMethod,
                               boolean isInitiator,
                               boolean isNotInitiator,
                               Long gameVariantId,
                               String userName,
                               String rulesVersion,
                               Integer playersNumber);

    List<GameVariantSummary> countGamesByVariant(Long gamerId);

    List<CurrentTurnDto> getCurrentTurns();
}
