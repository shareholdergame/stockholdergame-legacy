package com.stockholdergame.server.web.dto.game;

import java.util.Map;

public class Turn {

    public Map<Long, Integer> firstBuySellStep;

    public CardStep cardStep;

    public Map<Long, Integer> lastBuySellStep;
}
