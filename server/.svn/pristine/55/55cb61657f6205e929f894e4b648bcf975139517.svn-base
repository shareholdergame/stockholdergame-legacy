package com.stockholdergame.server.dao;

import com.stockholdergame.server.model.game.result.Statistics;

import java.util.List;

/**
 *
 */
public interface StatisticsDao {

    List<? extends Statistics> getUserStatistics(String userName, String statisticsVariant, int offset, int maxResults);

    long countUserStatistics(String userName, String statisticsVariant);
}
