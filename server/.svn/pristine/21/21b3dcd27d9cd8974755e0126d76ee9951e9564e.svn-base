package com.stockholdergame.server.gamebot.impl;

import com.stockholdergame.server.gamebot.BotPlayer;
import com.stockholdergame.server.gamebot.GameEventHandler;
import com.stockholdergame.server.gamebot.domain.GameBotEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("gameEventHandler")
@MessageEndpoint
public class GameEventHandlerImpl implements GameEventHandler {

    @Autowired
    private BotPlayer botPlayer;

    @Override
    public void handleEvent(GameBotEvent gameBotEvent) {
        botPlayer.play(gameBotEvent.getGameId(), gameBotEvent.getBotId());
    }
}
