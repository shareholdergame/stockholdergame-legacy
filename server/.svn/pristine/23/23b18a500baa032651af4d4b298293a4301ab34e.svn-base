package com.stockholdergame.server.model.game.variant;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Alexander Savin
 */
@Embeddable
public class GameCardGroupPk implements Serializable {

    private static final long serialVersionUID = 7296271420386948839L;

    @Column(name = "game_variant_id", nullable = false)
    private Long gameTemplateId;

    @Column(name = "card_group_id", nullable = false)
    private Long cardGroupId;

    public Long getGameTemplateId() {
        return gameTemplateId;
    }

    public void setGameTemplateId(Long gameTemplateId) {
        this.gameTemplateId = gameTemplateId;
    }

    public Long getCardGroupId() {
        return cardGroupId;
    }

    public void setCardGroupId(Long cardGroupId) {
        this.cardGroupId = cardGroupId;
    }
}
