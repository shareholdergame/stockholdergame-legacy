package com.stockholdergame.server.model.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Alexander Savin
 */
@Embeddable
public class ShareQuantityPk implements Serializable {

    private static final long serialVersionUID = -3375090325222486912L;

    @Column(name = "step_id", nullable = false)
    private Long stepId;

    @Column(name = "share_id", nullable = false)
    private Long shareId;

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
        if (!(o instanceof ShareQuantityPk)) {
            return false;
        }
        ShareQuantityPk sqPk = (ShareQuantityPk) o;
        return new EqualsBuilder()
                .append(stepId, sqPk.stepId)
                .append(shareId, sqPk.shareId)
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
