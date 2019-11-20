package com.stockholdergame.server.model.game;

/**
 *
 */
public class RelatedGameProjection {

    private Long gameId;

    private String gameLetter;

    public RelatedGameProjection(Long gameId, String gameLetter) {
        this.gameId = gameId;
        this.gameLetter = gameLetter;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameLetter() {
        return gameLetter;
    }

    public void setGameLetter(String gameLetter) {
        this.gameLetter = gameLetter;
    }
}
