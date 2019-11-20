package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.Identifiable;
import com.stockholdergame.server.model.game.variant.GameVariant;
import com.stockholdergame.server.model.game.variant.Rounding;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * @author Alexander Savin
 */
@Entity
/*@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)*/
@Table(name = "ag_games")
public class Game implements Identifiable<Long> {

    private static final long serialVersionUID = 6913626056807397109L;

    @Id
    @Column(name = "game_id")
    private Long id;

    @Column(name = "game_variant_id", nullable = false)
    @Deprecated
    private Long gameVariantId;

    @Column(name = "max_share_price", nullable = false)
    @Deprecated
    private Integer maxSharePrice;

    @Column(name = "share_price_step", nullable = false)
    @Deprecated
    private Integer sharePriceStep;

    @Enumerated(STRING)
    @Column(name = "rounding", nullable = false)
    private Rounding rounding = Rounding.U;

    @Column(name = "competitors_quantity", nullable = false)
    @Deprecated
    private Integer competitorsQuantity;

    @Column(name = "game_status_id", nullable = false)
    @Enumerated
    private GameStatus gameStatus;

    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @Column(name = "started_time")
    private Date startedTime;

    @Column(name = "finished_time")
    private Date finishedTime;

    @Column(name = "initiation_method")
    @Enumerated
    private GameInitiationMethod initiationMethod;

    @Column(name = "expired_time")
    private Date expiredTime;

    @Column(name = "game_label")
    private String label;

    @Column(name = "rules_version", nullable = false)
    @Deprecated
    private String rulesVersion;

    @Column(name = "game_letter", nullable = false)
    private String gameLetter;

    @OneToMany(mappedBy = "game", fetch = EAGER, cascade = CascadeType.ALL)
    private Set<Competitor> competitors;

    @OneToMany(mappedBy = "game", fetch = EAGER, cascade = CascadeType.ALL)
    private Set<Move> moves;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_variant_id", insertable = false, updatable = false)
    @Deprecated
    private GameVariant gameVariant;

    @ManyToOne(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_series_id", insertable = true, updatable = true)
    private GameSeries gameSeries;

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
    public Integer getMaxSharePrice() {
        return maxSharePrice;
    }

    @Deprecated
    public void setMaxSharePrice(Integer maxSharePrice) {
        this.maxSharePrice = maxSharePrice;
    }

    @Deprecated
    public Integer getSharePriceStep() {
        return sharePriceStep;
    }

    @Deprecated
    public void setSharePriceStep(Integer sharePriceStep) {
        this.sharePriceStep = sharePriceStep;
    }

    public Rounding getRounding() {
        return rounding;
    }

    public void setRounding(Rounding rounding) {
        this.rounding = rounding;
    }

    @Deprecated
    public Integer getCompetitorsQuantity() {
        return competitorsQuantity;
    }

    @Deprecated
    public void setCompetitorsQuantity(Integer competitorsQuantity) {
        this.competitorsQuantity = competitorsQuantity;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
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

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Set<Competitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Set<Competitor> competitors) {
        this.competitors = competitors;
    }

    public Set<Move> getMoves() {
        return moves;
    }

    public void setMoves(Set<Move> moves) {
        this.moves = moves;
    }

    @Deprecated
    public GameVariant getGameVariant() {
        return gameVariant;
    }

    @Deprecated
    public void setGameVariant(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
    }

    public GameInitiationMethod getInitiationMethod() {
        return initiationMethod;
    }

    public void setInitiationMethod(GameInitiationMethod initiationMethod) {
        this.initiationMethod = initiationMethod;
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

    public String getGameLetter() {
        return gameLetter;
    }

    public void setGameLetter(String gameLetter) {
        this.gameLetter = gameLetter;
    }

    public GameSeries getGameSeries() {
        return gameSeries;
    }

    public void setGameSeries(GameSeries gameSeries) {
        this.gameSeries = gameSeries;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("gameVariantId", gameVariantId)
            .append("maxSharePrice", maxSharePrice)
            .append("sharePriceStep", sharePriceStep)
            .append("competitorsQuantity", competitorsQuantity)
            .append("createdTime", createdTime)
            .append("startedTime", startedTime)
            .append("finishedTime", finishedTime)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Game)) {
            return false;
        }
        Game g = (Game) o;
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
