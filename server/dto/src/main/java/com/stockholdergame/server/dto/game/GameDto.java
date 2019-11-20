package com.stockholdergame.server.dto.game;

import com.stockholdergame.server.dto.game.result.CompetitorDiffDto;
import com.stockholdergame.server.dto.game.result.CompetitorResultDto;

import java.util.Date;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 13.10.2010 8.41.34
 */
public class GameDto {

    private Long id;

    private String label;

    private Long gameVariantId;

    private String rounding;

    private Integer competitorsQuantity;

    private String gameStatus;

    private Date createdTime;

    private Date startedTime;

    private Date finishedTime;

    private String gameLetter;

    private String rulesVersion;

    private Long gameSeriesId;

    private Boolean switchMoveOrder;

    private Set<RelatedGame> relatedGames;

    private Set<MoveDto> moves;

    private Set<CompetitorDto> competitors;

    private Set<CompetitorResultDto> competitorResults;

    private Set<CompetitorDiffDto> competitorDiffs;

    private Set<CompetitorResultDto> gameSeriesCompetitorResults;

    private Set<CompetitorDiffDto> gameSeriesCompetitorDiffs;

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

    public String getRounding() {
        return rounding;
    }

    public void setRounding(String rounding) {
        this.rounding = rounding;
    }

    public Integer getCompetitorsQuantity() {
        return competitorsQuantity;
    }

    public void setCompetitorsQuantity(Integer competitorsQuantity) {
        this.competitorsQuantity = competitorsQuantity;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
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

    public Long getGameSeriesId() {
        return gameSeriesId;
    }

    public void setGameSeriesId(Long gameSeriesId) {
        this.gameSeriesId = gameSeriesId;
    }

    public Boolean getSwitchMoveOrder() {
        return switchMoveOrder;
    }

    public void setSwitchMoveOrder(Boolean switchMoveOrder) {
        this.switchMoveOrder = switchMoveOrder;
    }

    public Set<RelatedGame> getRelatedGames() {
        return relatedGames;
    }

    public void setRelatedGames(Set<RelatedGame> relatedGames) {
        this.relatedGames = relatedGames;
    }

    public Set<MoveDto> getMoves() {
        return moves;
    }

    public void setMoves(Set<MoveDto> moves) {
        this.moves = moves;
    }

    public Set<CompetitorDto> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Set<CompetitorDto> competitors) {
        this.competitors = competitors;
    }

    public Set<CompetitorResultDto> getCompetitorResults() {
        return competitorResults;
    }

    public void setCompetitorResults(Set<CompetitorResultDto> competitorResults) {
        this.competitorResults = competitorResults;
    }

    public Set<CompetitorDiffDto> getCompetitorDiffs() {
        return competitorDiffs;
    }

    public void setCompetitorDiffs(Set<CompetitorDiffDto> competitorDiffs) {
        this.competitorDiffs = competitorDiffs;
    }

    public Set<CompetitorResultDto> getGameSeriesCompetitorResults() {
        return gameSeriesCompetitorResults;
    }

    public void setGameSeriesCompetitorResults(Set<CompetitorResultDto> gameSeriesCompetitorResults) {
        this.gameSeriesCompetitorResults = gameSeriesCompetitorResults;
    }

    public Set<CompetitorDiffDto> getGameSeriesCompetitorDiffs() {
        return gameSeriesCompetitorDiffs;
    }

    public void setGameSeriesCompetitorDiffs(Set<CompetitorDiffDto> gameSeriesCompetitorDiffs) {
        this.gameSeriesCompetitorDiffs = gameSeriesCompetitorDiffs;
    }
}
