package com.stockholdergame.server.web.dto.game;

import java.util.Set;
import java.util.TreeSet;

public class GameReport {

    public Long gameId;

    public Set<ReportPlayer> players;

    public TreeSet<ReportRound> rounds;
}
