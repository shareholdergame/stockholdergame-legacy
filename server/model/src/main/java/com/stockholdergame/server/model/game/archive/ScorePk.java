package com.stockholdergame.server.model.game.archive;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Alexander Savin
 *         Date: 22.1.13 22.35
 */
@Embeddable
public class ScorePk implements Serializable {

    private static final long serialVersionUID = 2100076533128743316L;

    @Column(name = "first_gamer_id", nullable = false)
    private Long firstGamerId;

    @Column(name = "second_gamer_id", nullable = false)
    private Long secondGamerId;

    @Column(name = "game_variant_id", nullable = false)
    private Long gameVariantId;

    @Column(name = "first_gamer_move_order", nullable = false)
    private Integer firstGamerMoveOrder;

    @Column(name = "rules_version", nullable = false)
    private String rulesVersion;

    public ScorePk() {
    }

    public ScorePk(Long firstGamerId, Long secondGamerId, Long gameVariantId, Integer firstGamerMoveOrder, String rulesVersion) {
        this.firstGamerId = firstGamerId;
        this.secondGamerId = secondGamerId;
        this.gameVariantId = gameVariantId;
        this.firstGamerMoveOrder = firstGamerMoveOrder;
        this.rulesVersion = rulesVersion;
    }

    public Long getFirstGamerId() {
        return firstGamerId;
    }

    public void setFirstGamerId(Long firstGamerId) {
        this.firstGamerId = firstGamerId;
    }

    public Long getSecondGamerId() {
        return secondGamerId;
    }

    public void setSecondGamerId(Long secondGamerId) {
        this.secondGamerId = secondGamerId;
    }

    public Long getGameVariantId() {
        return gameVariantId;
    }

    public void setGameVariantId(Long gameVariantId) {
        this.gameVariantId = gameVariantId;
    }

    public Integer getFirstGamerMoveOrder() {
        return firstGamerMoveOrder;
    }

    public void setFirstGamerMoveOrder(Integer firstGamerMoveOrder) {
        this.firstGamerMoveOrder = firstGamerMoveOrder;
    }

    public String getRulesVersion() {
        return rulesVersion;
    }

    public void setRulesVersion(String rulesVersion) {
        this.rulesVersion = rulesVersion;
    }
}
