package com.stockholdergame.server.services.common;

import com.stockholdergame.server.dto.ServerStatisticsDto;
import com.stockholdergame.server.dto.game.UserStatisticsFilterDto;
import com.stockholdergame.server.dto.game.UserStatisticsList;

/**
 * @author Alexander Savin
 *         Date: 25.12.12 17.20
 */
public interface StatisticsService {

    ServerStatisticsDto getServerStatistics();

    void collectServerStatistics();

    UserStatisticsList getUserStatistics(UserStatisticsFilterDto userFilter);
}
