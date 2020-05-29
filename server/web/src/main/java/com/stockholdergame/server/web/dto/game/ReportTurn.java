package com.stockholdergame.server.web.dto.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Set;

public class ReportTurn {

    public int round;

    public int turn;

    @ApiModelProperty(dataType = "String")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime finishedTime;

    public Set<ReportStep> steps;
}
