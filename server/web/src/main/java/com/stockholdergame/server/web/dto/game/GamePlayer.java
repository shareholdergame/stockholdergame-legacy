package com.stockholdergame.server.web.dto.game;

import java.util.Set;

public class GamePlayer implements Comparable<GamePlayer> {

    public String name;

    public int turnOrder;

    public Set<PlayerCard> playerCards;

    @Override
    public int compareTo(GamePlayer o) {
        return turnOrder - o.turnOrder;
    }
}
