package com.stockholdergame.server.web.dto.game;

import java.util.Set;

public class GameReport {

    public Long gameId;

    public Set<ReportPlayer> players;

    public Set<ReportRound> rounds;
}
