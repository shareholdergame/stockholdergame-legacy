package com.stockholdergame.server.model.game.variant;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Alexander Savin
 */
@Embeddable
public class GameSharePk implements Serializable {

    private static final long serialVersionUID = -7306610757417204824L;

    @Column(name = "share_id", nullable = false)
    private Long shareId;

    @Column(name = "game_variant_id", nullable = false)
    private Long gameVariantId;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getGameVariantId() {
        return gameVariantId;
    }

    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }
}
