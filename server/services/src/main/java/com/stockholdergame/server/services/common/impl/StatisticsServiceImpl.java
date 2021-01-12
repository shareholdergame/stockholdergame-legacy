package com.stockholdergame.server.services.common.impl;

import com.stockholdergame.server.dao.StatisticsDao;
import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.ServerStatisticsDto;
import com.stockholdergame.server.dto.account.UserDto;
import com.stockholdergame.server.dto.game.UserStatistics;
import com.stockholdergame.server.dto.game.UserStatisticsFilterDto;
import com.stockholdergame.server.dto.game.UserStatisticsList;
import com.stockholdergame.server.model.account.Sex;
import com.stockholdergame.server.model.game.result.Statistics;
import com.stockholdergame.server.services.common.StatisticsService;
import com.stockholdergame.server.services.security.UserSessionTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author Alexander Savin
 *         Date: 25.12.12 17.22
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private UserSessionTrackingService userSessionTrackingService;

    private ServerStatisticsDto latestStatistics;

    @Autowired
    private StatisticsDao statisticsDao;

    @PostConstruct
    private void init() {
        latestStatistics = new ServerStatisticsDto();
    }

    @Override
    public ServerStatisticsDto getServerStatistics() {
        return latestStatistics;
    }

    @Override
    public void collectServerStatistics() {
        latestStatistics.setOnlineUsersCount(userSessionTrackingService.getOnlineUsersCount());
        latestStatistics.setOnlineUsers(userSessionTrackingService.getOnlineUserNames(10));
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserStatisticsList getUserStatistics(UserStatisticsFilterDto userStatisticsFilter) {
        if (userStatisticsFilter.isShowTop10()) {
            userStatisticsFilter.setOffset(0);
            userStatisticsFilter.setMaxResults(10);
        }
        if (StringUtils.isEmpty(userStatisticsFilter.getStatisticsVariant())) {
            userStatisticsFilter.setStatisticsVariant(Statistics.STATISTICS_10);
        }
        long count = statisticsDao.countUserStatistics(userStatisticsFilter.getUserName(), userStatisticsFilter.getStatisticsVariant());
        List<Statistics> statisticsList =
                (List<Statistics>) statisticsDao.getUserStatistics(userStatisticsFilter.getUserName(), userStatisticsFilter.getStatisticsVariant(),
                        userStatisticsFilter.getOffset(), userStatisticsFilter.getMaxResults());
        List<UserStatistics> userStatistics = new ArrayList<>();
        for (Statistics statistics : statisticsList) {
            UserStatistics userStat = new UserStatistics();
            UserDto userDto = new UserDto();
            userDto.setUserName(statistics.getUserName());
            userDto.setLocale(statistics.getGamerAccount().getLocale().toString());
            userDto.setOnline(userSessionTrackingService.isUserOnline(userDto.getUserName()));
            userStat.setUser(userDto);
            if (!userStatisticsFilter.isShowTop10()) {
                userStat.getUser().setProfile(new ProfileDto());
                if (statistics.getGamerAccount().getProfile() != null) {
                    userStat.getUser().getProfile().setAvatar(statistics.getGamerAccount().getProfile().getAvatar());
                    Sex sex = statistics.getGamerAccount().getProfile().getSex();
                    userStat.getUser().getProfile().setSex(sex != null ? sex.name() : null);
                    userStat.getUser().getProfile().setCountry(statistics.getGamerAccount().getProfile().getCountry());
                    userStat.getUser().getProfile().setCity(statistics.getGamerAccount().getProfile().getCity());
                }

                userStat.setAllGamesCount(statistics.getAllGamesCount());
                userStat.setFirstOrderCount(statistics.getFirstOrderCount());
                userStat.setSecondOrderCount(statistics.getSecondOrderCount());
                userStat.setAllWinsCount(statistics.getAllWinsCount());
                userStat.setFirstOrderWinsCount(statistics.getFirstOrderWinsCount());
                userStat.setSecondOrderWinsCount(statistics.getSecondOrderWinsCount());
                userStat.setGameSeriesCount(statistics.getGameSeriesCount());
                userStat.setGameSeriesWinsCount(statistics.getGameSeriesWinsCount());
                userStat.setDrawsCount(statistics.getDrawsCount());
                userStat.setGamesCount(statistics.getGamesCount());
                userStat.setWinsCount(statistics.getWinsCount());
                userStat.setWinsRatio(statistics.getRatio() == -1 ? 0.0 : statistics.getRatio() * 100.00);
                userStat.setMaxTotal(statistics.getMaxTotal());
                userStat.setMaxDiff(statistics.getMaxDiff());
                userStat.setTotalWinned(statistics.getTotalWinned());

                userStat.setBankruptsCount(statistics.getBankruptsCount());

                if (statistics.getLastPlay() != null) {
                    userStat.setDaysAfterLastPlay(getDateDiff(statistics.getLastPlay()));
                } else {
                    userStat.setDaysAfterLastPlay(-1L);
                }
                if (statistics.getLastVisit() != null) {
                    userStat.setDaysAfterLastVisit(getDateDiff(statistics.getLastVisit()));
                } else {
                    userStat.setDaysAfterLastVisit(-1L);
                }
            }
            userStatistics.add(userStat);
        }
        return new UserStatisticsList(count, userStatistics);
    }

    private long getDateDiff(Date date) {
        long timeDiff = System.currentTimeMillis() - date.getTime();
        return TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }
}
