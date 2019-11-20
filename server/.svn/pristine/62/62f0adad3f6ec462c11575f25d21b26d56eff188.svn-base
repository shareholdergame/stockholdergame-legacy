package com.stockholdergame.server.model.game;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "ag_move_steps",
        uniqueConstraints = @UniqueConstraint(columnNames = {"competitor_move_id", "step_type"}))
public class MoveStep implements Serializable, Comparable<MoveStep> {

    private static final long serialVersionUID = 7498951236414388170L;

    @Id
    @GeneratedValue
    @Column(name = "step_id")
    private Long id;

    @Column(name = "step_type", nullable = false)
    @Enumerated
    private StepType stepType;

    @Column(name = "cash_value", nullable = false)
    private Integer cashValue;

    @ManyToOne
    @JoinColumn(name = "original_step_id")
    private MoveStep originalStep;

    @ManyToOne
    @JoinColumn(name = "competitor_move_id")
    private CompetitorMove competitorMove;

    @OneToMany(mappedBy = "step", fetch = EAGER, cascade = ALL)
    private Set<ShareQuantity> shareQuantities;

    @OneToMany(mappedBy = "step", fetch = EAGER, cascade = ALL)
    private Set<SharePrice> sharePrices;

    @OneToMany(mappedBy = "step", fetch = EAGER, cascade = ALL)
    private Set<Compensation> compensations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }

    public Integer getCashValue() {
        return cashValue;
    }

    public void setCashValue(Integer cashValue) {
        this.cashValue = cashValue;
    }

    public MoveStep getOriginalStep() {
        return originalStep;
    }

    public void setOriginalStep(MoveStep originalStep) {
        this.originalStep = originalStep;
    }

    public CompetitorMove getCompetitorMove() {
        return competitorMove;
    }

    public void setCompetitorMove(CompetitorMove competitorMove) {
        this.competitorMove = competitorMove;
    }

    public Set<ShareQuantity> getShareQuantities() {
        return shareQuantities;
    }

    public void setShareQuantities(Set<ShareQuantity> shareQuantities) {
        this.shareQuantities = shareQuantities;
    }

    public Set<SharePrice> getSharePrices() {
        return sharePrices;
    }

    public void setSharePrices(Set<SharePrice> sharePrices) {
        this.sharePrices = sharePrices;
    }

    public Set<Compensation> getCompensations() {
        return compensations;
    }

    public void setCompensations(Set<Compensation> compensations) {
        this.compensations = compensations;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("stepType", stepType)
                .append("cashValue", cashValue)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MoveStep)) {
            return false;
        }
        MoveStep g = (MoveStep) o;
        return new EqualsBuilder()
                .append(stepType, g.stepType)
                .append(originalStep, g.originalStep)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(stepType)
                .append(originalStep)
                .toHashCode();
    }

    public int compareTo(MoveStep o) {
        int result = 0;
        result = this.stepType.ordinal() - o.stepType.ordinal();
        if (result == 0) {
            if (this.originalStep == null || o.originalStep == null) {
                result = 0;
            } else {
                result = (int) (this.originalStep.id - o.originalStep.id);
            }
        }
        return result;
    }
}
