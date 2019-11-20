package com.stockholdergame.server.model.game.archive;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FinishedGameCompetitorPk implements Serializable {

    private static final long serialVersionUID = 2926236856620468796L;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "gamer_id", nullable = false)
    private Long gamerId;

    public FinishedGameCompetitorPk() {
    }

    public FinishedGameCompetitorPk(Long gameId, Long gamerId) {
        this.gameId = gameId;
        this.gamerId = gamerId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }
}
