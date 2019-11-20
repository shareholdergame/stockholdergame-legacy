package com.stockholdergame.server.services.game;

import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.GameDto;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.session.UserInfo;

/**
 * Play game service.
 */
public interface PlayGameService {

    GameDto doMove(DoMoveDto doMoveDto);

    GameDto doMove(DoMoveDto doMoveDto, UserInfo userInfo);

    void doMove(DoMoveDto doMoveDto, GameState gameState);

    void removeGameFromRegistry(Long gameId);

    void clearGameRegistry();
}
