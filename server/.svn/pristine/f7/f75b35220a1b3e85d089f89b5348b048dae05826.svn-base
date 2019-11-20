package com.stockholdergame.server.model.game.archive;

import com.stockholdergame.server.model.game.variant.GameVariant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/**
 * @author Aliaksandr Savin
 */
@Entity
@Table(name = "fg_game_series")
public class FinishedGameSeries implements Serializable {

    private static final long serialVersionUID = -767794066617922722L;

    @Id
    @Column(name = "game_series_id", nullable = false)
    private Long id;

    @Column(name = "game_series_number")
    private Integer gameSeriesNumber;

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

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "started_time")
    private Date startedTime;

    @Column(name = "finished_time")
    private Date finishedTime;

    @Column(name = "rules_version")
    private String rulesVersion;

    @OneToMany(mappedBy = "gameSeries", fetch = LAZY, cascade = CascadeType.ALL)
    private Set<FinishedGame> games;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_variant_id", insertable = false, updatable = false)
    private GameVariant gameVariant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGameSeriesNumber() {
        return gameSeriesNumber;
    }

    public void setGameSeriesNumber(Integer gameSeriesNumber) {
        this.gameSeriesNumber = gameSeriesNumber;
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

    public String getRulesVersion() {
        return rulesVersion;
    }

    public void setRulesVersion(String rulesVersion) {
        this.rulesVersion = rulesVersion;
    }

    public Set<FinishedGame> getGames() {
        return games;
    }

    public void setGames(Set<FinishedGame> games) {
        this.games = games;
    }

    public GameVariant getGameVariant() {
        return gameVariant;
    }

    public void setGameVariant(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("gameSeriesId", id)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FinishedGameSeries)) {
            return false;
        }
        FinishedGameSeries gs = (FinishedGameSeries) o;
        return new EqualsBuilder()
            .append(id, gs.id)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }
}
