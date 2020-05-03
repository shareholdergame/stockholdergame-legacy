package com.stockholdergame.server.web.dto;

import com.stockholdergame.server.web.dto.game.Game;
import com.stockholdergame.server.web.dto.game.GameSet;

import java.util.List;

/**
 * Date: 11/01/2018
 *
 * @author Aliaksandr Savin
 */
public class GameListResponse {

    public Pagination pagination;

    public List<GameSet> items;

    public static GameListResponse of(List<GameSet> items, Pagination pagination) {
        GameListResponse glr = new GameListResponse();
        glr.items = items;
        glr.pagination = pagination;
        return glr;
    }
}
