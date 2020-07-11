package com.stockholdergame.server.web.dto.game;

import java.util.Set;

public class Game {

    public Long id;

    public GameLetter letter;

    public GameStatus status;

    public PlayPosition position;

    public Set<GamePlayer> players;

    public GameReport gameReport;

    public Set<TurnOrderResult> result;
}
