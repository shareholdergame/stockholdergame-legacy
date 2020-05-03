package com.stockholdergame.server.web.dto.game;

import java.time.LocalDateTime;
import java.util.Set;

public class ReportRound {

    public int round;

    public Set<ReportTurn> turns;
}
