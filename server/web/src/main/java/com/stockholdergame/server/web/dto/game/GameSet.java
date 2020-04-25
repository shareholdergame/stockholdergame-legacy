package com.stockholdergame.server.web.dto.game;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;

public class GameSet {

    public Long id;

    public Set<GameOption> options;

    public String label;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime createdTime;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime startedTime;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    public LocalDateTime finishedTime;

    public GameStatus status;

    public Set<GamePlayer> players;

    public Set<PlayerResult> result;

    public Set<Game> games;
}
