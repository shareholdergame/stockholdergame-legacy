package com.stockholdergame.server.web.dto.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class ReportTurn implements Comparable<ReportTurn> {

    public int round;

    public int turn;

    public Long appliedCardId;

    public Long cardId;

    @ApiModelProperty(dataType = "String")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime finishedTime;

    public Map<StepType, ReportStep> steps;

    public long total = 0;

    @Override
    public int compareTo(ReportTurn o) {
        int result = this.turn - o.turn;

        return result != 0 ? result : this.round - o.round;
    }
}
