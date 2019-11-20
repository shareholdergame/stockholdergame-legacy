package com.stockholdergame.server.model.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Alexander Savin
 *         Date: 12.12.2010 14.06.16
 */
@Embeddable
public class CompensationPk implements Serializable {

    private static final long serialVersionUID = 7862249381519248855L;

    @Column(name = "step_id", nullable = false)
    private Long stepId;

    @Column(name = "share_id", nullable = false)
    private Long shareId;

    public CompensationPk() {
    }

    public CompensationPk(Long stepId, Long shareId) {
        this.stepId = stepId;
        this.shareId = shareId;
    }

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("stepId", stepId)
                .append("shareId", shareId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompensationPk)) {
            return false;
        }
        CompensationPk cPk = (CompensationPk) o;
        return new EqualsBuilder()
                .append(stepId, cPk.stepId)
                .append(shareId, cPk.shareId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(stepId)
                .append(shareId)
                .toHashCode();
    }
}
