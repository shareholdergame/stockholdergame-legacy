package com.stockholdergame.server.web.dto.game;

import com.stockholdergame.server.web.dto.player.Player;

public class GamePlayer {

    public Long id;

    public boolean bot;

    public boolean initiator;

    public Player player;

    public PlayerInvitation invitation;
}
