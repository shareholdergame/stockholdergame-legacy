package com.stockholdergame.server.model.game.result;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/**
 *
 */
@Entity
@Table(name = "g_game_series_result")
public class GameSeriesResult implements Serializable {

    private static final long serialVersionUID = 3617103132593965045L;

    @Id
    @Column(name = "game_series_id", nullable = false)
    private Long gameSeriesId;

    @Column(name = "game_variant_id", nullable = false)
    private Long gameVariantId;

    @Column(name = "switch_move_order", nullable = false)
    @Type(type = "boolean")
    private Boolean switchMoveOrder;

    @Column(name = "play_with_bot", nullable = false)
    @Type(type = "boolean")
    private Boolean playWithBot;

    @Column(name = "competitors_quantity", nullable = false)
    private Integer competitorsQuantity;

    @Column(name = "rules_version")
    private String rulesVersion;

    @Column(name = "finished_time")
    private Date finishedTime;

    @OneToMany(mappedBy = "gameSeriesResult", fetch = LAZY, cascade = CascadeType.ALL)
    private Set<CompetitorResult> competitorResults;

    @OneToMany(mappedBy = "gameSeriesResult", fetch = LAZY, cascade = CascadeType.ALL)
    private Set<CompetitorDiff> competitorDiffs;

    public Long getGameSeriesId() {
        return gameSeriesId;
    }

    public void setGameSeriesId(Long gameSeriesId) {
        this.gameSeriesId = gameSeriesId;
    }

    public Long getGameVariantId() {
        return gameVariantId;
    }

    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    public Boolean getSwitchMoveOrder() {
        return switchMoveOrder;
    }

    public void setSwitchMoveOrder(Boolean switchMoveOrder) {
        this.switchMoveOrder = switchMoveOrder;
    }

    public Boolean getPlayWithBot() {
        return playWithBot;
    }

    public void setPlayWithBot(Boolean playWithBot) {
        this.playWithBot = playWithBot;
    }

    public Integer getCompetitorsQuantity() {
        return competitorsQuantity;
    }

    public void setCompetitorsQuantity(Integer competitorsQuantity) {
        this.competitorsQuantity = competitorsQuantity;
    }

    public String getRulesVersion() {
        return rulesVersion;
    }

    public void setRulesVersion(String rulesVersion) {
        this.rulesVersion = rulesVersion;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Set<CompetitorResult> getCompetitorResults() {
        return competitorResults;
    }

    public void setCompetitorResults(Set<CompetitorResult> competitorResults) {
        this.competitorResults = competitorResults;
    }

    public Set<CompetitorDiff> getCompetitorDiffs() {
        return competitorDiffs;
    }

    public void setCompetitorDiffs(Set<CompetitorDiff> competitorDiffs) {
        this.competitorDiffs = competitorDiffs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("gameSeriesId", gameSeriesId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameSeriesResult)) {
            return false;
        }
        GameSeriesResult g = (GameSeriesResult) o;
        return new EqualsBuilder()
                .append(gameSeriesId, g.gameSeriesId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(gameSeriesId)
                .toHashCode();
    }
}
