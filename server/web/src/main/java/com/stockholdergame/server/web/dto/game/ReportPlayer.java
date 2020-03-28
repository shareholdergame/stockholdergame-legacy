package com.stockholdergame.server.web.dto.game;

import java.util.Set;

public class ReportPlayer {

    public Long playerId;

    public short turnOrder;

    public Set<PlayerCard> playerCards;
}
