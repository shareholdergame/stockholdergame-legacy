package com.stockholdergame.server.model.game.variant;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Alexander Savin
 */
@Embeddable
public class CardOperationPk implements Serializable {

    private static final long serialVersionUID = -1124513633948705209L;

    @Column(name = "price_oper_id", nullable = false)
    private Long priceOperId;

    @Column(name = "card_id", nullable = false)
    private Long cardId;

    public Long getPriceOperId() {
        return priceOperId;
    }

    public void setPriceOperId(Long priceOperId) {
        this.priceOperId = priceOperId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
