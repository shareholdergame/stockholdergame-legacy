package com.stockholdergame.server.web.dto.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

public class ReportStep {

    public StepType stepType;

    public long cash;

    public Map<Long, ShareAmount> shares;

    public Map<Long, SharePrice> sharePrices;

    //public Map<Long, ShareCompensation> compensations;

    //public Long originalStepId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReportStep step = (ReportStep) o;

        return new EqualsBuilder()
                .append(stepType, step.stepType)
                //.append(originalStepId, step.originalStepId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(stepType)
                //.append(originalStepId)
                .toHashCode();
    }
}
