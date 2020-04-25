package com.stockholdergame.server.web.dto;

import com.stockholdergame.server.web.dto.player.Player;

import java.util.List;

public class FriendsResponse {

    private Pagination pagination;

    private List<Player> players;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
