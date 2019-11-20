package com.stockholdergame.server.dto.game;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 */
@Deprecated
public class GameSeriesResultDto {

    private String userName;

    private byte[] avatar;

    private boolean isBot;

    private String locale;

    private boolean winner;

    private int totalFunds;

    private int totalPoints;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(int totalFunds) {
        this.totalFunds = totalFunds;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userName).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameSeriesResultDto)) {
            return false;
        }
        GameSeriesResultDto g = (GameSeriesResultDto) o;
        return new EqualsBuilder()
                .append(userName, g.userName)
                .isEquals();
    }
}
