package com.stockholdergame.server.dto.account;

import com.stockholdergame.server.dto.PaginationDto;

/**
 * @author Alexander Savin
 *         Date: 28.5.11 23.05
 */
public class UserFilterDto extends PaginationDto {

    private String userName;

    private String[] userNames;

    private String locale;

    private String sex;

    private String country;

    private String city;

    private FriendFilterType[] friendFilters;

    private String[] excludedUserNames;

    private Long gameId;

    private boolean showRemoved;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getUserNames() {
        return userNames;
    }

    public void setUserNames(String[] userNames) {
        this.userNames = userNames;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public FriendFilterType[] getFriendFilters() {
        return friendFilters;
    }

    public void setFriendFilters(FriendFilterType[] friendFilters) {
        this.friendFilters = friendFilters;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId == 0L ? null : gameId;
    }

    public String[] getExcludedUserNames() {
        return excludedUserNames;
    }

    public void setExcludedUserNames(String[] excludedUserNames) {
        this.excludedUserNames = excludedUserNames;
    }

    public boolean isShowRemoved() {
        return showRemoved;
    }

    public void setShowRemoved(boolean showRemoved) {
        this.showRemoved = showRemoved;
    }
}
