package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.model.Identifiable;
import javax.persistence.*;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name ="gv_card_operations")
public class CardOperation implements Identifiable<CardOperationPk> {

    private static final long serialVersionUID = 8626184457606360252L;

    @Id
    private CardOperationPk id;

    @Column(name = "share_id")
    private Long shareId;

    @ManyToOne
    @JoinColumn(name = "price_oper_id", insertable = false, updatable = false)
    private PriceOperation priceOperation;

    @ManyToOne
    @JoinColumn(name = "card_id", insertable = false, updatable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "share_id", insertable = false, updatable = false)
    private Share share;

    public CardOperationPk getId() {
        return id;
    }

    public void setId(CardOperationPk id) {
        this.id = id;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public PriceOperation getPriceOperation() {
        return priceOperation;
    }

    public void setPriceOperation(PriceOperation priceOperation) {
        this.priceOperation = priceOperation;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }
}
