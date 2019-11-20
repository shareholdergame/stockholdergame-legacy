package com.stockholdergame.server.dto.game;

import com.stockholdergame.server.dto.PaginationDto;

/**
 *
 */
public class UserStatisticsFilterDto extends PaginationDto {

    private String userName;

    private String statisticsVariant;

    private boolean showTop10;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatisticsVariant() {
        return statisticsVariant;
    }

    public void setStatisticsVariant(String statisticsVariant) {
        this.statisticsVariant = statisticsVariant;
    }

    public boolean isShowTop10() {
        return showTop10;
    }

    public void setShowTop10(boolean isTop10) {
        this.showTop10 = isTop10;
    }
}
