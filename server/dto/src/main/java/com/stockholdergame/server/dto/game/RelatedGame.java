package com.stockholdergame.server.dto.game;

import com.stockholdergame.server.dto.game.result.CompetitorDiffDto;
import com.stockholdergame.server.dto.game.result.CompetitorResultDto;
import com.stockholdergame.server.model.game.GameStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Game series DTO.
 */
public class RelatedGame {

    private Long gameId;

    private String gameLetter;

    private String status;

    private Set<CompetitorResultDto> competitorResults = new HashSet<>();

    private Set<CompetitorDiffDto> competitorDiffs = new HashSet<>();

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameLetter() {
        return gameLetter;
    }

    public void setGameLetter(String gameLetter) {
        this.gameLetter = gameLetter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gameId).toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RelatedGame)) {
            return false;
        }
        RelatedGame g = (RelatedGame) o;
        return new EqualsBuilder()
                .append(gameId, g.gameId)
                .isEquals();
    }
}
