package com.stockholdergame.server.model.game.result;

import com.stockholdergame.server.model.account.GamerAccount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

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
@Table(name = "g_competitor_results",
    uniqueConstraints = @UniqueConstraint(columnNames = {"game_series_id", "game_id", "gamer_id"}))
public class CompetitorResult implements Serializable {

    private static final long serialVersionUID = 6054459121793687448L;

    @Id
    @GeneratedValue
    @Column(name = "competitor_result_id")
    private Long id;

    @Column(name = "game_series_id", nullable = false)
    private Long gameSeriesId;

    @Column(name = "game_id", nullable = true)
    private Long gameId;

    @Column(name = "gamer_id", nullable = false)
    private Long gamerId;

    @Column(name = "move_order")
    private Integer moveOrder;

    @Column(name = "is_winner", nullable = false)
    @Type(type = "boolean")
    private Boolean isWinner = false;

    @Column(name = "is_draw", nullable = false)
    @Type(type = "boolean")
    private Boolean isDraw = false;

    @Column(name = "is_out", nullable = false)
    @Type(type = "boolean")
    private Boolean isOut = false;

    @Column(name = "total_funds")
    private Integer totalFunds;

    @Column(name = "total_points")
    private Float totalPoints;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "game_series_id", insertable = false, updatable = false)
    private GameSeriesResult gameSeriesResult;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gamer_id", insertable = false, updatable = false)
    private GamerAccount gamerAccount;

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

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean winner) {
        isWinner = winner;
    }

    public Boolean getDraw() {
        return isDraw;
    }

    public void setDraw(Boolean draw) {
        isDraw = draw;
    }

    public Boolean getOut() {
        return isOut;
    }

    public void setOut(Boolean out) {
        isOut = out;
    }

    public Integer getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(Integer totalFunds) {
        this.totalFunds = totalFunds;
    }

    public Float getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Float totalPoints) {
        this.totalPoints = totalPoints;
    }

    public GameSeriesResult getGameSeriesResult() {
        return gameSeriesResult;
    }

    public void setGameSeriesResult(GameSeriesResult gameSeriesResult) {
        this.gameSeriesResult = gameSeriesResult;
    }

    public GamerAccount getGamerAccount() {
        return gamerAccount;
    }

    public void setGamerAccount(GamerAccount gamerAccount) {
        this.gamerAccount = gamerAccount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("gameSeriesId", gameSeriesId)
            .append("gameId", gameId)
            .append("gamerId", gamerId)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompetitorResult)) {
            return false;
        }
        CompetitorResult g = (CompetitorResult) o;
        return new EqualsBuilder()
            .append(gameSeriesId, g.gameSeriesId)
            .append(gameId, g.gameId)
            .append(gamerId, g.gamerId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(gameSeriesId)
            .append(gameId)
            .append(gamerId)
            .toHashCode();
    }
}
