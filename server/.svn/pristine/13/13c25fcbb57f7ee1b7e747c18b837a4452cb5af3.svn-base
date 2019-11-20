package com.stockholdergame.server.model.game.variant;

import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 20.11.2010 19.19.46
 */
public interface GameVariantInfo {

    Long getGameVariantId();

    int getMovesQuantity();

    boolean hasShare(Long shareId);

    boolean hasCard(Long cardId);

    boolean hasCardOperation(Long cardId, Long priceOperationId);

    Set<Long> getShareIds();

    Set<Long> getCardGroupIds();

    Set<Long> getCardIdsInGroup(Long cardGroupId);

    Integer getGamerCardQuantity(Long cardGroupId);

    Set<Long> getPriceOperationIds(Long appliedCardId);

    Long getShareIdForPriceOperation(Long cardId, Long priceOperationId);

    PriceOperation getPriceOperation(Long cardId, Long priceOperationId);
}
