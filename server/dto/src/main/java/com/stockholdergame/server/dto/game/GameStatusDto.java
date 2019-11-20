package com.stockholdergame.server.dto.game;

/**
 * @author Alexander Savin
 *         Date: 22.3.2010 21.25.35
 */
public class GameStatusDto {

    private Long gameId;

    private String gameStatus;

    public GameStatusDto() {
    }

    public GameStatusDto(Long gameId, String gameStatus) {
        this.gameId = gameId;
        this.gameStatus = gameStatus;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
