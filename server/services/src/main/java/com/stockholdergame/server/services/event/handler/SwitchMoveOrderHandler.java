package com.stockholdergame.server.services.event.handler;

import com.stockholdergame.server.services.event.EventHandler;
import com.stockholdergame.server.services.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aliaksandr Savin
 */
@Component("switchMoveOrderHandler")
public class SwitchMoveOrderHandler implements EventHandler<Long> {

    @Autowired
    private GameService gameService;

    @Override
    public void handle(Long userId, Long gameId) {
        gameService.startGameWithPredefinedMoveOrder(gameId);
    }
}
