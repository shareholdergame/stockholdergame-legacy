package com.stockholdergame.server.dto.game;

/**
 * @author Alexander Savin
 *         Date: 17.6.13 22.39
 */
public class ScoreValuesDto {

    private int winningsCount;

    private int defeatsCount;

    private int bankruptsCount;

    public int getWinningsCount() {
        return winningsCount;
    }

    public void setWinningsCount(int winningsCount) {
        this.winningsCount = winningsCount;
    }

    public int getDefeatsCount() {
        return defeatsCount;
    }

    public void setDefeatsCount(int defeatsCount) {
        this.defeatsCount = defeatsCount;
    }

    public int getBankruptsCount() {
        return bankruptsCount;
    }

    public void setBankruptsCount(int bankruptsCount) {
        this.bankruptsCount = bankruptsCount;
    }
}
