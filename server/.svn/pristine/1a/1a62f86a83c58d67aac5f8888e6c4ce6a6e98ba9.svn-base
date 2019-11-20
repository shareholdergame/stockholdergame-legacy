package com.stockholdergame.server.model.game;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Alexander Savin
 *         Date: 12.12.2010 14.05.26
 */
@Entity
@Table(name = "ag_compensations")
public class Compensation implements Serializable {

    private static final long serialVersionUID = 910524095983205934L;

    @Id
    private CompensationPk id;

    @Column(name = "sum_value")
    private Integer sum;

    @MapsId("stepId")
    @ManyToOne
    @JoinColumn(name = "step_id")
    private MoveStep step;

    public Compensation() {
    }

    public Compensation(Long stepId,
                        Long shareId,
                        Integer sum,
                        MoveStep step) {
        this.id = new CompensationPk(stepId, shareId);
        this.sum = sum;
        this.step = step;
    }

    public CompensationPk getId() {
        return id;
    }

    public void setId(CompensationPk id) {
        this.id = id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public MoveStep getStep() {
        return step;
    }

    public void setStep(MoveStep step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("sum", sum)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Compensation)) {
            return false;
        }
        Compensation g = (Compensation) o;
        return new EqualsBuilder()
                .append(id, g.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }
}
