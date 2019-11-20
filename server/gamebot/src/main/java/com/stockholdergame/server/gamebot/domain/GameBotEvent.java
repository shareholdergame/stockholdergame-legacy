package com.stockholdergame.server.gamebot.domain;

/**
 *
 */
public class GameBotEvent {

    private Long gameId;

    private Long botId;

    public GameBotEvent(Long gameId, Long botId) {
        this.gameId = gameId;
        this.botId = botId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getBotId() {
        return botId;
    }
}
