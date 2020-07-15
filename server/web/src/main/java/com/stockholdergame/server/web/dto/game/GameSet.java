package com.stockholdergame.server.web.dto.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Set;

public class GameSet {

    public Long id;

    public GameOptions options;

    public String label;

    @ApiModelProperty(dataType = "String")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime createdTime;

    @ApiModelProperty(dataType = "String")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime startedTime;

    @ApiModelProperty(dataType = "String")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime finishedTime;

    public GameStatus status;

    public Set<GameSetPlayer> players;

    public Set<PlayerResult> result;

    public Set<Game> games;
}
