package com.stockholdergame.server.web.dto.player;

import java.io.Serializable;

/**
 * Date: 10/08/2018
 *
 * @author Aliaksandr Savin
 */
public class Player implements Serializable {

    public String name;

    public boolean bot;

    public String avatarUrl;

    public boolean online;

    public boolean removed;

    public boolean friend;
}
