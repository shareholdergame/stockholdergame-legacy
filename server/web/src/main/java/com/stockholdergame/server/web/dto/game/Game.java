package com.stockholdergame.server.web.dto.game;

import java.util.Set;
import java.util.TreeSet;

public class Game {

    public Long id;

    public GameLetter letter;

    public GameStatus status;

    public PlayPosition position;

    public Set<GamePlayer> players;

    public TreeSet<ReportRound> rounds;

    public Set<GameResult> result;
}
