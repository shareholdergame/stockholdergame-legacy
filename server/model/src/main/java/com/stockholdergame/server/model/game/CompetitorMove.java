package com.stockholdergame.server.model.game;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "ag_competitor_moves",
        uniqueConstraints = @UniqueConstraint(columnNames = {"move_id", "move_order"}))
public class CompetitorMove implements Serializable, Comparable<CompetitorMove> {

    private static final long serialVersionUID = -3863412296426985935L;

    @Id
    @GeneratedValue
    @Column(name = "competitor_move_id")
    private Long id;

    @Column(name = "move_order", nullable = false)
    private Integer moveOrder;

    @Column(name = "applied_card_id")
    private Long appliedCardId;

    @Column(name = "finished_time")
    private Date finishedTime;

    @ManyToOne
    @JoinColumn(name = "move_id")
    private Move move;

    @ManyToOne
    @JoinColumn(name = "competitor_id", nullable = false)
    private Competitor competitor;

    @OneToMany(mappedBy = "competitorMove", fetch = EAGER, cascade = CascadeType.ALL)
    private List<MoveStep> steps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public Long getAppliedCardId() {
        return appliedCardId;
    }

    public void setAppliedCardId(Long appliedCardId) {
        this.appliedCardId = appliedCardId;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public List<MoveStep> getSteps() {
        return steps;
    }

    public void setSteps(List<MoveStep> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("moveOrder", moveOrder)
                .append("appliedCardId", appliedCardId)
                .append("finishedTime", finishedTime)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompetitorMove)) {
            return false;
        }
        CompetitorMove g = (CompetitorMove) o;
        return new EqualsBuilder()
                .append(moveOrder, g.moveOrder)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(moveOrder)
                .toHashCode();
    }

    public int compareTo(CompetitorMove o) {
        return this.moveOrder - o.moveOrder;
    }
}
