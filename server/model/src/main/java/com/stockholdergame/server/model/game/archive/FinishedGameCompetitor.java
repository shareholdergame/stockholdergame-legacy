package com.stockholdergame.server.model.game.archive;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "fg_competitors")
public class FinishedGameCompetitor implements Serializable {

    private static final long serialVersionUID = -1195606096413566931L;

    @Id
    private FinishedGameCompetitorPk id;

    @Column(name = "is_winner", nullable = false)
    @Type(type = "boolean")
    @Deprecated
    private Boolean isWinner = false;

    @Column(name = "is_out", nullable = false)
    @Type(type = "boolean")
    private Boolean isOut = false;

    @Column(name = "total_funds")
    @Deprecated
    private Integer totalFunds;

    @Column(name = "move_order")
    private Integer moveOrder;

    @MapsId("gameId")
    @ManyToOne
    @JoinColumn(name = "game_id")
    private FinishedGame game;

    public FinishedGameCompetitorPk getId() {
        return id;
    }

    public void setId(FinishedGameCompetitorPk id) {
        this.id = id;
    }

    @Deprecated
    public Boolean getWinner() {
        return isWinner;
    }

    @Deprecated
    public void setWinner(Boolean winner) {
        isWinner = winner;
    }

    public Boolean getOut() {
        return isOut;
    }

    public void setOut(Boolean out) {
        isOut = out;
    }

    @Deprecated
    public Integer getTotalFunds() {
        return totalFunds;
    }

    @Deprecated
    public void setTotalFunds(Integer totalFunds) {
        this.totalFunds = totalFunds;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public FinishedGame getGame() {
        return game;
    }

    public void setGame(FinishedGame game) {
        this.game = game;
    }
}
