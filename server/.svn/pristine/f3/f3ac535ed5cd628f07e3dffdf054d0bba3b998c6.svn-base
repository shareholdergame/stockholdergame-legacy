package com.stockholdergame.server.model.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 */
@Embeddable
@Deprecated
public class GameSeriesResultPk implements Serializable {

    private static final long serialVersionUID = 6073422892233211542L;

    @Column(name = "game_series_id", nullable = false)
    private Long gameSeriesId;

    @Column(name = "gamer_id", nullable = false)
    private Long gamerId;

    public Long getGameSeriesId() {
        return gameSeriesId;
    }

    public void setGameSeriesId(Long gameSeriesId) {
        this.gameSeriesId = gameSeriesId;
    }

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("gameSeriesId", gameSeriesId)
            .append("gamerId", gamerId)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameSeriesResultPk)) {
            return false;
        }
        GameSeriesResultPk gsrPk = (GameSeriesResultPk) o;
        return new EqualsBuilder()
            .append(gameSeriesId, gsrPk.gameSeriesId)
            .append(gamerId, gsrPk.gamerId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(gameSeriesId)
            .append(gamerId)
            .toHashCode();
    }
}
