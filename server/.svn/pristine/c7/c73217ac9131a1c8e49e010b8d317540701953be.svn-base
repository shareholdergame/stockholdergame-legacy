package com.stockholdergame.server.services.event.handler;

import com.stockholdergame.server.services.event.EventHandler;
import com.stockholdergame.server.services.game.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Aliaksandr Savin
 */
@Component("startGameHandler")
public class StartGamesEventHandler implements EventHandler<Set<Long>> {

    @Autowired
    private GameService gameService;

    @Override
    public void handle(Long userId, Set<Long> gameIds) {
        gameService.tryToStartGames(gameIds);
    }
}
