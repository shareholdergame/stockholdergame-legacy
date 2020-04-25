package com.stockholdergame.server.web.dto;

import com.stockholdergame.server.web.dto.player.Player;
import com.stockholdergame.server.web.dto.player.PlayerSession;

import java.io.Serializable;

/**
 * Date: 10/05/2018
 *
 * @author Aliaksandr Savin
 */
public class PlayerAchievements implements Serializable {

    public Player player;

    public Location location;

    public PlayerSession playerSession;

    public Achievements achievements;
}
