package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;
import javax.persistence.*;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "gv_cards_quantity")
public class CardQuantity implements Identifiable<CardQuantityPk> {

    private static final long serialVersionUID = 8079618013948109073L;

    @Id
    private CardQuantityPk id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @ManyToOne
    @JoinColumn(name = "card_group_id", insertable = false, updatable = false)
    private CardGroup cardGroup;

    @ManyToOne
    @JoinColumn(name = "card_id", insertable = false, updatable = false)
    private Card card;

    public CardQuantityPk getId() {
        return id;
    }

    public void setId(CardQuantityPk id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public CardGroup getCardGroup() {
        return cardGroup;
    }

    public void setCardGroup(CardGroup cardGroup) {
        this.cardGroup = cardGroup;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
