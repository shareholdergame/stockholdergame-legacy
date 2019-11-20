package com.stockholdergame.server.model.game.variant;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "gv_game_card_groups")
public class GameCardGroup implements Serializable {

    private static final long serialVersionUID = 5207729933074568899L;

    @Id
    private GameCardGroupPk id;

    @Column(name = "gamer_card_quantity", nullable = false)
    private Integer gamerCardQuantity;

    @ManyToOne
    @JoinColumn(name = "card_group_id", insertable = false, updatable = false)
    private CardGroup cardGroup;

    @ManyToOne
    @JoinColumn(name = "game_variant_id", insertable = false, updatable = false)
    private GameVariant gameVariant;

    public GameCardGroupPk getId() {
        return id;
    }

    public void setId(GameCardGroupPk id) {
        this.id = id;
    }

    public Integer getGamerCardQuantity() {
        return gamerCardQuantity;
    }

    public void setGamerCardQuantity(Integer gamerCardQuantity) {
        this.gamerCardQuantity = gamerCardQuantity;
    }

    public CardGroup getCardGroup() {
        return cardGroup;
    }

    public void setCardGroup(CardGroup cardGroup) {
        this.cardGroup = cardGroup;
    }

    public GameVariant getGameVariant() {
        return gameVariant;
    }

    public void setGameVariant(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
    }
}
