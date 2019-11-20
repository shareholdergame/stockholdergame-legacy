package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.Identifiable;
import com.stockholdergame.server.model.account.GamerAccount;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "ag_competitors",
        uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "gamer_id", "move_order"}))
public class Competitor implements Identifiable<Long>, Comparable<Competitor> {

    private static final long serialVersionUID = -7266086902020024263L;

    @Id
    @GeneratedValue
    @Column(name = "competitor_id")
    private Long id;

    @Column(name = "gamer_id", nullable = false)
    private Long gamerId;

    @Column(name = "move_order")
    private Integer moveOrder;

    @Column(name = "joined_time", nullable = false)
    private Date joinedTime;

    @Column(name = "is_initiator", nullable = false)
    @Type(type = "boolean")
    private Boolean isInitiator;

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

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @OneToMany(mappedBy = "competitor", fetch = EAGER, cascade = CascadeType.ALL)
    private Set<CompetitorCard> competitorCards;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gamer_id", insertable = false, updatable = false)
    private GamerAccount gamerAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGamerId() {
        return gamerId;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public Date getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public Boolean getInitiator() {
        return isInitiator;
    }

    public void setInitiator(Boolean initiator) {
        isInitiator = initiator;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<CompetitorCard> getCompetitorCards() {
        return competitorCards;
    }

    public void setCompetitorCards(Set<CompetitorCard> competitorCards) {
        this.competitorCards = competitorCards;
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
                .append("gamerId", gamerId)
                .append("moveOrder", moveOrder)
                .append("joinedTime", joinedTime)
                .append("isInitiator", isInitiator)
                .append("isWinner", isWinner)
                .append("isOut", isOut)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Competitor)) {
            return false;
        }
        Competitor g = (Competitor) o;
        return new EqualsBuilder()
                .append(gamerId, g.gamerId)
                .append(moveOrder, g.moveOrder)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(gamerId)
                .append(moveOrder)
                .toHashCode();
    }

    public int compareTo(Competitor o) {
        return this.moveOrder - o.moveOrder;
    }
}
