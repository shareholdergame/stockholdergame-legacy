package com.stockholdergame.server.web.dto.game;

import java.util.Set;
import java.util.TreeSet;

public class ReportRound implements Comparable<ReportRound> {

    public int round;

    public TreeSet<ReportTurn> turns;

    @Override
    public int compareTo(ReportRound o) {
        return this.round - o.round;
    }
}
