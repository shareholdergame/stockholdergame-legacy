package com.stockholdergame.server.web.dto;

import java.util.List;

/**
 * Date: 10/11/2018
 *
 * @author Aliaksandr Savin
 */
public class PlayerAchievementsResponse {

    public Filter filter;

    public Pagination pagination;

    public List<PlayerAchievements> items;
}
