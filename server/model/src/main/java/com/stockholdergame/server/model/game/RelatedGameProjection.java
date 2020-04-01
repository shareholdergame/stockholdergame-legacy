package com.stockholdergame.server.model.game;

/**
 *
 */
public class RelatedGameProjection {

    private Long gameId;

    private String gameLetter;

    private GameStatus status;

    public RelatedGameProjection(Long gameId, String gameLetter) {
        this.gameId = gameId;
        this.gameLetter = gameLetter;
        this.status = GameStatus.FINISHED;
    }

    public RelatedGameProjection(Long gameId, String gameLetter, GameStatus status) {
        this.gameId = gameId;
        this.gameLetter = gameLetter;
        this.status = status;
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

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
