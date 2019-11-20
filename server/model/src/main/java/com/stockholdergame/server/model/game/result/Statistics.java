package com.stockholdergame.server.model.game.result;

import com.stockholdergame.server.model.account.GamerAccount;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

/**
 *
 */
@MappedSuperclass
public class Statistics {

    public static final String STATISTICS_8 = "Statistics8";
    public static final String STATISTICS_10 = "Statistics10";
    public static final String STATISTICS_12 = "Statistics12";
    public static final String STATISTICS_3 = "Statistics3";

    @Id
    @Column(name = "gamer_id")
    private Long gamerId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "all_games_count")
    private Integer allGamesCount;

    @Column(name = "first_order_count")
    private Integer firstOrderCount;

    @Column(name = "second_order_count")
    private Integer secondOrderCount;

    @Column(name = "all_wins_count")
    private Integer allWinsCount;

    @Column(name = "first_order_wins_count")
    private Integer firstOrderWinsCount;

    @Column(name = "second_order_wins_count")
    private Integer secondOrderWinsCount;

    @Column(name = "game_series_count")
    private Integer gameSeriesCount;

    @Column(name = "game_series_wins_count")
    private Integer gameSeriesWinsCount;

    @Column(name = "draws_count")
    private Integer drawsCount;

    @Column(name = "games_count")
    private Integer gamesCount;

    @Column(name = "wins_count")
    private Integer winsCount;

    @Column(name = "bankrupts_count")
    private Integer bankruptsCount;

    @Column(name = "max_diff")
    private Integer maxDiff;

    @Column(name = "max_total")
    private Integer maxTotal;

    @Column(name = "total_winned")
    private Integer totalWinned;

    @Column(name = "last_play")
    private Date lastPlay;

    @Column(name = "last_session")
    private Date lastVisit;

    @Column(name = "ratio")
    private Double ratio;

    @Column(name = "not_actual")
    private int notActual;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "gamer_id", insertable = false, updatable = false)
    private GamerAccount gamerAccount;

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAllGamesCount() {
        return allGamesCount;
    }

    public void setAllGamesCount(Integer allGamesCount) {
        this.allGamesCount = allGamesCount;
    }

    public Integer getFirstOrderCount() {
        return firstOrderCount;
    }

    public void setFirstOrderCount(Integer firstOrderCount) {
        this.firstOrderCount = firstOrderCount;
    }

    public Integer getSecondOrderCount() {
        return secondOrderCount;
    }

    public void setSecondOrderCount(Integer secondOrderCount) {
        this.secondOrderCount = secondOrderCount;
    }

    public Integer getAllWinsCount() {
        return allWinsCount;
    }

    public void setAllWinsCount(Integer allWinsCount) {
        this.allWinsCount = allWinsCount;
    }

    public Integer getFirstOrderWinsCount() {
        return firstOrderWinsCount;
    }

    public void setFirstOrderWinsCount(Integer firstOrderWinsCount) {
        this.firstOrderWinsCount = firstOrderWinsCount;
    }

    public Integer getSecondOrderWinsCount() {
        return secondOrderWinsCount;
    }

    public void setSecondOrderWinsCount(Integer secondOrderWinsCount) {
        this.secondOrderWinsCount = secondOrderWinsCount;
    }

    public Integer getGameSeriesCount() {
        return gameSeriesCount;
    }

    public void setGameSeriesCount(Integer gameSeriesCount) {
        this.gameSeriesCount = gameSeriesCount;
    }

    public Integer getGameSeriesWinsCount() {
        return gameSeriesWinsCount;
    }

    public void setGameSeriesWinsCount(Integer gameSeriesWinsCount) {
        this.gameSeriesWinsCount = gameSeriesWinsCount;
    }

    public Integer getDrawsCount() {
        return drawsCount;
    }

    public void setDrawsCount(Integer drawsCount) {
        this.drawsCount = drawsCount;
    }

    public Integer getGamesCount() {
        return gamesCount;
    }

    public void setGamesCount(Integer gamesCount) {
        this.gamesCount = gamesCount;
    }

    public Integer getWinsCount() {
        return winsCount;
    }

    public void setWinsCount(Integer winsCount) {
        this.winsCount = winsCount;
    }

    public Integer getBankruptsCount() {
        return bankruptsCount;
    }

    public void setBankruptsCount(Integer bankruptsCount) {
        this.bankruptsCount = bankruptsCount;
    }

    public Integer getMaxDiff() {
        return maxDiff;
    }

    public void setMaxDiff(Integer maxDiff) {
        this.maxDiff = maxDiff;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getTotalWinned() {
        return totalWinned;
    }

    public void setTotalWinned(Integer totalWinned) {
        this.totalWinned = totalWinned;
    }

    public Date getLastPlay() {
        return lastPlay;
    }

    public void setLastPlay(Date lastPlay) {
        this.lastPlay = lastPlay;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public int getNotActual() {
        return notActual;
    }

    public void setNotActual(int notActual) {
        this.notActual = notActual;
    }

    public GamerAccount getGamerAccount() {
        return gamerAccount;
    }

    public void setGamerAccount(GamerAccount gamerAccount) {
        this.gamerAccount = gamerAccount;
    }
}
