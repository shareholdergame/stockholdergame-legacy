package com.stockholdergame.server.gamebot;

import com.stockholdergame.server.gamebot.domain.GameBotEvent;

/**
 * Game events handler
 */
public interface GameEventHandler {

    void handleEvent(GameBotEvent gameBotEvent);
}
