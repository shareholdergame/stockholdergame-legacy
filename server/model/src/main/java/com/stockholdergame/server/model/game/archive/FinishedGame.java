package com.stockholdergame.server.model.game.archive;

import com.stockholdergame.server.model.game.variant.Rounding;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.stockholdergame.server.model.ModelConstants.TINY_BATCH_SIZE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "fg_games")
public class FinishedGame implements Serializable {

    private static final long serialVersionUID = -4160086473721054878L;

    @Id
    @Column(name = "game_id")
    private Long id;

    @Column(name = "game_variant_id", nullable = false)
    @Deprecated
    private Long gameVariantId;

    @Column(name = "competitors_quantity", nullable = false)
    @Deprecated
    private Integer competitorsQuantity;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "started_time")
    private Date startedTime;

    @Column(name = "finished_time")
    private Date finishedTime;

    @Column(name = "game_label")
    private String label;

    @Column(name = "rules_version")
    @Deprecated
    private String rulesVersion;

    @Enumerated(STRING)
    @Column(name = "rounding", nullable = false)
    private Rounding rounding = Rounding.U;

    @Column(name = "game_letter", nullable = false)
    private String gameLetter;

    @Column(name = "game_object")
    @Lob
    private byte[] gameObject;

    @OneToMany(mappedBy = "game", fetch = EAGER, cascade = CascadeType.ALL)
    @BatchSize(size = TINY_BATCH_SIZE)
    private Set<FinishedGameCompetitor> competitors;

    @ManyToOne(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_series_id")
    private FinishedGameSeries gameSeries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Deprecated
    public Long getGameVariantId() {
        return gameVariantId;
    }

    @Deprecated
    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    @Deprecated
    public Integer getCompetitorsQuantity() {
        return competitorsQuantity;
    }

    @Deprecated
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Deprecated
    public String getRulesVersion() {
        return rulesVersion;
    }

    @Deprecated
    public void setRulesVersion(String ruleVersion) {
        this.rulesVersion = ruleVersion;
    }

    public Rounding getRounding() {
        return rounding;
    }

    public void setRounding(Rounding rounding) {
        this.rounding = rounding;
    }

    public String getGameLetter() {
        return gameLetter;
    }

    public void setGameLetter(String gameLetter) {
        this.gameLetter = gameLetter;
    }

    public byte[] getGameObject() {
        return gameObject;
    }

    public void setGameObject(byte[] gameObject) {
        this.gameObject = gameObject;
    }

    public Set<FinishedGameCompetitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Set<FinishedGameCompetitor> competitors) {
        this.competitors = competitors;
    }

    public FinishedGameSeries getGameSeries() {
        return gameSeries;
    }

    public void setGameSeries(FinishedGameSeries gameSeries) {
        this.gameSeries = gameSeries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("gameVariantId", gameVariantId)
            .append("competitorsQuantity", competitorsQuantity)
            .append("createdTime", createdTime)
            .append("startedTime", startedTime)
            .append("finishedTime", finishedTime)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FinishedGame)) {
            return false;
        }
        FinishedGame g = (FinishedGame) o;
        return new EqualsBuilder()
            .append(id, g.id)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }
}
