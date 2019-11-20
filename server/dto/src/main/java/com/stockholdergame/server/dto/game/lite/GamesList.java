package com.stockholdergame.server.dto.game.lite;

import java.util.List;

public class GamesList {

    public GamesList(int totalCount, List<GameLite> games) {
        this.totalCount = totalCount;
        this.games = games;
    }

    private int totalCount;

    private List<GameLite> games;

    public int getTotalCount() {
        return totalCount;
    }

    public List<GameLite> getGames() {
        return games;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setGames(List<GameLite> games) {
        this.games = games;
    }
}
