package com.stockholdergame.server.web.dto;

import com.stockholdergame.server.web.dto.player.PlayerWithLocation;

import java.util.List;

/**
 * Date: 11/01/2018
 *
 * @author Aliaksandr Savin
 */
public class PlayerListResponse {

    public Pagination pagination;

    public List<PlayerWithLocation> players;
}
