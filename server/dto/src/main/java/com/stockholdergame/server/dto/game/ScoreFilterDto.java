package com.stockholdergame.server.dto.game;

import com.stockholdergame.server.dto.PaginationDto;

/**
 *
 */
public class ScoreFilterDto extends PaginationDto {

    private boolean totalScoreOnly;

    private String userName;

    private boolean legacyRules;

    public boolean isTotalScoreOnly() {
        return totalScoreOnly;
    }

    public void setTotalScoreOnly(boolean totalScoreOnly) {
        this.totalScoreOnly = totalScoreOnly;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLegacyRules() {
        return legacyRules;
    }

    public void setLegacyRules(boolean legacyRules) {
        this.legacyRules = legacyRules;
    }
}
