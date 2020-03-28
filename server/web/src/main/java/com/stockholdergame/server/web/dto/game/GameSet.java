package com.stockholdergame.server.web.dto.game;

import java.time.LocalDateTime;
import java.util.Set;

public class GameSet {

    public Long id;

    public Set<GameOption> options;

    public String label;

    public LocalDateTime createdTime;

    public LocalDateTime startedTime;

    public LocalDateTime finishedTime;

    public GameStatus status;

    public Set<GamePlayer> players;

    public Set<PlayerResult> result;

    public Set<Game> games;
}
