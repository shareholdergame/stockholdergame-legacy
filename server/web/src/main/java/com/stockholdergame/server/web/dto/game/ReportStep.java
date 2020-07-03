package com.stockholdergame.server.web.dto.game;

import java.util.Map;

public class ReportStep {

    public StepType stepType;

    public long cashValue;

    public Map<Long, ShareAmount> shares;

    public Map<Long, SharePrice> sharePrices;

    public Map<Long, ShareCompensation> compensations;

    public Long originalStepId;
}
