package com.stockholdergame.server.web.dto.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Set;

public class ReportTurn implements Comparable<ReportTurn> {

    public int round;

    public int turn;

    @ApiModelProperty(dataType = "String")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime finishedTime;

    public Set<ReportStep> steps;

    @Override
    public int compareTo(ReportTurn o) {
        int result = this.turn - o.turn;

        return result != 0 ? result : this.round - o.round;
    }
}
