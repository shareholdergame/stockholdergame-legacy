package com.stockholdergame.server.dto.game.result;

/**
 *
 */
public class CompetitorDiffDto {

    private Long gameSeriesId;

    private Long gameId;

    private String firstUserName;

    private String secondUserName;

    private Integer fundsAbsoluteDiff;

    private Double fundsRelativeDiff;

    public Long getGameSeriesId() {
        return gameSeriesId;
    }

    public void setGameSeriesId(Long gameSeriesId) {
        this.gameSeriesId = gameSeriesId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getFirstUserName() {
        return firstUserName;
    }

    public void setFirstUserName(String firstUserName) {
        this.firstUserName = firstUserName;
    }

    public String getSecondUserName() {
        return secondUserName;
    }

    public void setSecondUserName(String secondUserName) {
        this.secondUserName = secondUserName;
    }

    public Integer getFundsAbsoluteDiff() {
        return fundsAbsoluteDiff;
    }

    public void setFundsAbsoluteDiff(Integer fundsAbsoluteDiff) {
        this.fundsAbsoluteDiff = fundsAbsoluteDiff;
    }

    public Double getFundsRelativeDiff() {
        return fundsRelativeDiff;
    }

    public void setFundsRelativeDiff(Double fundsRelativeDiff) {
        this.fundsRelativeDiff = fundsRelativeDiff;
    }
}
