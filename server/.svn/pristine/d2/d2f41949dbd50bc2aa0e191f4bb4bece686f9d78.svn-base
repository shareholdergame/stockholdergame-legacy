package com.stockholdergame.server.model.game.variant;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Alexander Savin
 */
@Embeddable
public class CardQuantityPk implements Serializable {

    private static final long serialVersionUID = -2640190442183979601L;

    @Column(name = "card_id", nullable = false)
    private Long cardId;

    @Column(name = "card_group_id", nullable = false)
    private Long cardGroupId;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCardGroupId() {
        return cardGroupId;
    }

    public void setCardGroupId(Long cardGroupId) {
        this.cardGroupId = cardGroupId;
    }
}
