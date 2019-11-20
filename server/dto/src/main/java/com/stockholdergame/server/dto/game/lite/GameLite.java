package com.stockholdergame.server.dto.game.lite;

import java.util.Date;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 18.6.11 10.59
 */
public class GameLite {

    private Long id;

    private String label;

    private Long gameVariantId;

    private String gameVariantName;

    private Integer movesQuantity;

    private Integer competitorsQuantity;

    private String rounding;

    private Date createdTime;

    private Date startedTime;

    private Date finishedTime;

    private String gameStatus;

    private Integer lastMoveNumber;

    private Integer lastMoveOrder;

    private Date lastMoveTime;

    private Set<CompetitorLite> competitors;

    private Set<ShareLite> shares;

    private String initiationMethod;

    private String gameLetter;

    private String rulesVersion;

    private Boolean switchMoveOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getGameVariantId() {
        return gameVariantId;
    }

    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    public String getGameVariantName() {
        return gameVariantName;
    }

    public void setGameVariantName(String gameVariantName) {
        this.gameVariantName = gameVariantName;
    }

    public Integer getMovesQuantity() {
        return movesQuantity;
    }

    public void setMovesQuantity(Integer movesQuantity) {
        this.movesQuantity = movesQuantity;
    }

    public Integer getCompetitorsQuantity() {
        return competitorsQuantity;
    }

    public void setCompetitorsQuantity(Integer competitorsQuantity) {
        this.competitorsQuantity = competitorsQuantity;
    }

    public String getRounding() {
        return rounding;
    }

    public void setRounding(String rounding) {
        this.rounding = rounding;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Integer getLastMoveNumber() {
        return lastMoveNumber;
    }

    public void setLastMoveNumber(Integer lastMoveNumber) {
        this.lastMoveNumber = lastMoveNumber;
    }

    public Integer getLastMoveOrder() {
        return lastMoveOrder;
    }

    public void setLastMoveOrder(Integer lastMoveOrder) {
        this.lastMoveOrder = lastMoveOrder;
    }

    public Date getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(Date lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public Set<CompetitorLite> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Set<CompetitorLite> competitors) {
        this.competitors = competitors;
    }

    public Set<ShareLite> getShares() {
        return shares;
    }

    public void setShares(Set<ShareLite> shares) {
        this.shares = shares;
    }

    public String getInitiationMethod() {
        return initiationMethod;
    }

    public void setInitiationMethod(String initiationMethod) {
        this.initiationMethod = initiationMethod;
    }

    public String getGameLetter() {
        return gameLetter;
    }

    public void setGameLetter(String gameLetter) {
        this.gameLetter = gameLetter;
    }

    public String getRulesVersion() {
        return rulesVersion;
    }

    public void setRulesVersion(String rulesVersion) {
        this.rulesVersion = rulesVersion;
    }

    public Boolean getSwitchMoveOrder() {
        return switchMoveOrder;
    }

    public void setSwitchMoveOrder(Boolean switchMoveOrder) {
        this.switchMoveOrder = switchMoveOrder;
    }
}
