package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;
import java.util.Set;
import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "gv_price_scales",
        uniqueConstraints = @UniqueConstraint(columnNames = {"max_value", "scale_spacing"}))
public class PriceScale implements Identifiable<Long> {

    private static final long serialVersionUID = 1297929325825050694L;

    @Id
    @GeneratedValue
    @Column(name = "scale_id")
    private Long id;

    @Column(name = "max_value", nullable = false)
    private Integer maxValue;

    @Column(name = "scale_spacing", nullable = false)
    private Integer scaleSpacing;

    @OneToMany(mappedBy = "priceScale", fetch = EAGER)
    private Set<GameVariant> gameVariants;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getScaleSpacing() {
        return scaleSpacing;
    }

    public void setScaleSpacing(Integer scaleSpacing) {
        this.scaleSpacing = scaleSpacing;
    }

    public Set<GameVariant> getGameVariants() {
        return gameVariants;
    }

    public void setGameVariants(Set<GameVariant> gameVariants) {
        this.gameVariants = gameVariants;
    }
}
