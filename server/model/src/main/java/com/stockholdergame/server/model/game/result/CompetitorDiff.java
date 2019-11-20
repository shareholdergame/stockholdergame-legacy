package com.stockholdergame.server.model.game.result;

import com.stockholdergame.server.model.account.GamerAccount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 *
 */
@Entity
@Table(name = "g_competitor_diffs",
    uniqueConstraints = @UniqueConstraint(columnNames = {"game_series_id", "game_id", "first_gamer_id", "second_gamer_id"}))
public class CompetitorDiff implements Serializable {

    private static final long serialVersionUID = -6010844112930854163L;

    @Id
    @GeneratedValue
    @Column(name = "competitor_diff_id")
    private Long id;

    @Column(name = "game_series_id", nullable = false)
    private Long gameSeriesId;

    @Column(name = "game_id", nullable = true)
    private Long gameId;

    @Column(name = "first_gamer_id", nullable = false)
    private Long firstGamerId;

    @Column(name = "second_gamer_id", nullable = false)
    private Long secondGamerId;

    @Column(name = "funds_abs_diff")
    private Integer fundsAbsoluteDiff;

    @Column(name = "funds_rel_diff")
    private Double fundsRelativeDiff;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "game_series_id", insertable = false, updatable = false)
    private GameSeriesResult gameSeriesResult;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "first_gamer_id", insertable = false, updatable = false)
    private GamerAccount firstGamerAccount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "second_gamer_id", insertable = false, updatable = false)
    private GamerAccount secondGamerAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameSeriesId() {
        return gameSeriesId;
    }

    public void setGameSeriesId(Long gameSeriesId) {
        this.gameSeriesId = gameSeriesId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getFirstGamerId() {
        return firstGamerId;
    }

    public void setFirstGamerId(Long firstGamerId) {
        this.firstGamerId = firstGamerId;
    }

    public Long getSecondGamerId() {
        return secondGamerId;
    }

    public void setSecondGamerId(Long secondGamerId) {
        this.secondGamerId = secondGamerId;
    }

    public Integer getFundsAbsoluteDiff() {
        return fundsAbsoluteDiff;
    }

    public void setFundsAbsoluteDiff(Integer fundsAbsoluteDiff) {
        this.fundsAbsoluteDiff = fundsAbsoluteDiff;
    }

    public Double getFundsRelativeDiff() {
        return fundsRelativeDiff;
    }

    public void setFundsRelativeDiff(Double fundsRelativeDiff) {
        this.fundsRelativeDiff = fundsRelativeDiff;
    }

    public GameSeriesResult getGameSeriesResult() {
        return gameSeriesResult;
    }

    public void setGameSeriesResult(GameSeriesResult gameSeriesResult) {
        this.gameSeriesResult = gameSeriesResult;
    }

    public GamerAccount getFirstGamerAccount() {
        return firstGamerAccount;
    }

    public void setFirstGamerAccount(GamerAccount firstGamerAccount) {
        this.firstGamerAccount = firstGamerAccount;
    }

    public GamerAccount getSecondGamerAccount() {
        return secondGamerAccount;
    }

    public void setSecondGamerAccount(GamerAccount secondGamerAccount) {
        this.secondGamerAccount = secondGamerAccount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("gameSeriesId", gameSeriesId)
            .append("gameId", gameId)
            .append("firstGamerId", firstGamerId)
            .append("secondGamerId", secondGamerId)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompetitorDiff)) {
            return false;
        }
        CompetitorDiff g = (CompetitorDiff) o;
        return new EqualsBuilder()
            .append(gameSeriesId, g.gameSeriesId)
            .append(gameId, g.gameId)
            .append(firstGamerId, g.firstGamerId)
            .append(secondGamerId, g.secondGamerId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(gameSeriesId)
            .append(gameId)
            .append(firstGamerId)
            .append(secondGamerId)
            .toHashCode();
    }
}
