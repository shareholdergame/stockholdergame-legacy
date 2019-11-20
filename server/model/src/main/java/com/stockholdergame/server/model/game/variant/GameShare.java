package com.stockholdergame.server.model.game.variant;

import java.io.Serializable;
import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "gv_game_shares")
public class GameShare implements Serializable {

    private static final long serialVersionUID = -3453303286355574014L;

    @Id
    private GameSharePk id;

    @Column(name = "init_price", nullable = false)
    private Integer initPrice;

    @Column(name = "init_quantity", nullable = false)
    private Integer initQuantity;

    @Column(name = "color", nullable = false)
    @Enumerated(STRING)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "share_id", insertable = false, updatable = false)
    private Share share;

    @ManyToOne
    @JoinColumn(name = "game_variant_id", insertable = false, updatable = false)
    private GameVariant gameVariant;

    public GameSharePk getId() {
        return id;
    }

    public void setId(GameSharePk id) {
        this.id = id;
    }

    public Integer getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(Integer initPrice) {
        this.initPrice = initPrice;
    }

    public Integer getInitQuantity() {
        return initQuantity;
    }

    public void setInitQuantity(Integer initQuantity) {
        this.initQuantity = initQuantity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public GameVariant getGameVariant() {
        return gameVariant;
    }

    public void setGameVariant(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
    }
}
