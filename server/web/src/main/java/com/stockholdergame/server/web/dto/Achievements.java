package com.stockholdergame.server.web.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date: 10/08/2018
 *
 * @author Aliaksandr Savin
 */
public class Achievements implements Serializable {

    public int totalPlayed;

    public int wins;

    public int draws;

    public int bankrupts;

    public double winPercent;

    public int maxTotalSum;

    public int maxWonSum;

    public int totalWonSum;
}
