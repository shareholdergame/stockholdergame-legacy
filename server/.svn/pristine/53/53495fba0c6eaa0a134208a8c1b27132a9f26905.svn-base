package com.stockholdergame.server.dto.game;

import java.util.Set;
import javax.validation.constraints.NotNull;

/**
 * @author Alexander Savin
 *         Date: 23.10.2010 10.36.22
 */
public class DoMoveDto {

    @NotNull
    private Long gameId;

    @NotNull
    private Long appliedCardId;

    private Set<BuySellDto> firstBuySellActions;

    private Set<BuySellDto> lastBuySellActions;

    @NotNull
    private Set<PriceOperationDto> priceOperations;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getAppliedCardId() {
        return appliedCardId;
    }

    public void setAppliedCardId(Long appliedCardId) {
        this.appliedCardId = appliedCardId;
    }

    public Set<BuySellDto> getFirstBuySellActions() {
        return firstBuySellActions;
    }

    public void setFirstBuySellActions(Set<BuySellDto> firstBuySellActions) {
        this.firstBuySellActions = firstBuySellActions;
    }

    public Set<BuySellDto> getLastBuySellActions() {
        return lastBuySellActions;
    }

    public void setLastBuySellActions(Set<BuySellDto> lastBuySellActions) {
        this.lastBuySellActions = lastBuySellActions;
    }

    public Set<PriceOperationDto> getPriceOperations() {
        return priceOperations;
    }

    public void setPriceOperations(Set<PriceOperationDto> priceOperations) {
        this.priceOperations = priceOperations;
    }
}
