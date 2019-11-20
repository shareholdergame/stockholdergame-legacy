package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.account.GamerAccount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

/**
 *
 */
//@Entity
//@Table(name = "ag_game_series_result")
@Deprecated
public class GameSeriesResult implements Serializable {

    private static final long serialVersionUID = 3653788026327480220L;

    @Id
    private GameSeriesResultPk id;

    @Column(name = "is_winner", nullable = false)
    @Type(type = "boolean")
    private Boolean isWinner = false;

    @Column(name = "total_funds")
    private Integer totalFunds;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gamer_id", insertable = false, updatable = false)
    private GamerAccount gamerAccount;

    public GameSeriesResultPk getId() {
        return id;
    }

    public void setId(GameSeriesResultPk id) {
        this.id = id;
    }

    public Boolean getWinner() {
        return isWinner;
    }

    public void setWinner(Boolean isWinner) {
        this.isWinner = isWinner;
    }

    public Integer getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(Integer totalFunds) {
        this.totalFunds = totalFunds;
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
                .append("id", id)
                .append("isWinner", isWinner)
                .append("totalFunds", totalFunds)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameSeriesResult)) {
            return false;
        }
        GameSeriesResult gsr = (GameSeriesResult) o;
        return new EqualsBuilder()
                .append(id, gsr.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }
}
