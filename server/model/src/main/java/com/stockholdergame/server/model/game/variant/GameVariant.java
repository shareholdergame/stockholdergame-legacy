package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "gv_game_variants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"scale_id", "max_gamers_quantity",
                "rounding", "moves_quantity", "gamer_initial_cash"}))
public class GameVariant implements Identifiable<Long> {

    private static final long serialVersionUID = -512225093540393326L;

    @Id
    @GeneratedValue
    @Column(name = "game_variant_id")
    private Long id;

    @Column(name = "variant_name", nullable = false)
    private String name;

    @Column(name = "max_gamers_quantity", nullable = false)
    private Integer maxGamersQuantity;

    @Enumerated(STRING)
    @Column(name = "rounding", nullable = false)
    private Rounding rounding;

    @Column(name = "moves_quantity", nullable = false)
    private Integer movesQuantity;

    @Column(name = "gamer_initial_cash", nullable = false)
    private Integer gamerInitialCash;

    @Column(name = "is_active", nullable = false)
    @Type(type = "boolean")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "scale_id", insertable = false, updatable = false)
    private PriceScale priceScale;

    @OneToMany(mappedBy = "gameVariant", fetch = EAGER)
    private Set<GameCardGroup> cardGroups;

    @OneToMany(mappedBy = "gameVariant", fetch = EAGER)
    private Set<GameShare> shares;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxGamersQuantity() {
        return maxGamersQuantity;
    }

    public void setMaxGamersQuantity(Integer maxGamersQuantity) {
        this.maxGamersQuantity = maxGamersQuantity;
    }

    public Rounding getRounding() {
        return rounding;
    }

    public void setRounding(Rounding rounding) {
        this.rounding = rounding;
    }

    public Integer getMovesQuantity() {
        return movesQuantity;
    }

    public void setMovesQuantity(Integer movesQuantity) {
        this.movesQuantity = movesQuantity;
    }

    public Integer getGamerInitialCash() {
        return gamerInitialCash;
    }

    public void setGamerInitialCash(Integer gamerInitialCash) {
        this.gamerInitialCash = gamerInitialCash;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public PriceScale getPriceScale() {
        return priceScale;
    }

    public void setPriceScale(PriceScale priceScale) {
        this.priceScale = priceScale;
    }

    public Set<GameCardGroup> getCardGroups() {
        return cardGroups;
    }

    public void setCardGroups(Set<GameCardGroup> cardGroups) {
        this.cardGroups = cardGroups;
    }

    public Set<GameShare> getShares() {
        return shares;
    }

    public void setShares(Set<GameShare> shares) {
        this.shares = shares;
    }
}
