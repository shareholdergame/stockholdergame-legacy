package com.stockholdergame.server.util.game;

import static com.stockholdergame.server.model.game.move.ValidationError.INVALID_APPLIED_CARD;
import static com.stockholdergame.server.model.game.move.ValidationError.INVALID_SHARE_ID;
import static com.stockholdergame.server.model.game.move.ValidationError.MOVE_ORDER_VIOLATION;
import static com.stockholdergame.server.model.game.move.ValidationError.SHARE_DUPLICATED;
import static com.stockholdergame.server.model.game.move.ValidationError.SHARE_NOT_SPECIFIED;
import static com.stockholdergame.server.model.game.move.ValidationResult.SUCCESS_RESULT;
import com.stockholdergame.server.dto.game.BuySellDto;
import com.stockholdergame.server.dto.game.DoMoveDto;
import com.stockholdergame.server.dto.game.PriceOperationDto;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.model.game.CompetitorAccountExtraData;
import com.stockholdergame.server.model.game.move.ValidationError;
import com.stockholdergame.server.model.game.move.ValidationResult;
import com.stockholdergame.server.model.game.variant.GameVariantInfo;
import org.apache.commons.lang.Validate;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public final class MoveValidator {

    private DoMoveDto doMoveDto;

    private GameState gameState;

    private GameVariantInfo gameVariantInfo;

    private Long currentUserId;

    private MoveValidator(DoMoveDto doMoveDto, GameState gameState, GameVariantInfo gameVariantInfo, Long currentUserId) {
        this.doMoveDto = doMoveDto;
        this.gameState = gameState;
        this.gameVariantInfo = gameVariantInfo;
        this.currentUserId = currentUserId;
    }

    public static ValidationResult validate(DoMoveDto doMoveDto, GameState gameState, GameVariantInfo gameVariantInfo, Long currentUserId) {
        Validate.notNull(doMoveDto);
        Validate.notNull(gameState);
        Validate.notNull(gameVariantInfo);
        Validate.notNull(currentUserId);

        return new MoveValidator(doMoveDto, gameState, gameVariantInfo, currentUserId).validate();
    }

    private ValidationResult validate() {
        ValidationResult result = validateMoveOrder();

        if (result.isSuccess()) {
            result = validateAppliedCard();
        }

        if (result.isSuccess()) {
            result = validatePriceOperations();
        }

        if (result.isSuccess()) {
            result = validateBuySellOperations();
        }

        return result;
    }

    private ValidationResult validateMoveOrder() {
        Long currentGamerId = gameState.getCurrentCompetitorState().<CompetitorAccountExtraData>getExtraData().getGamerId();
        return currentUserId.equals(currentGamerId) ? SUCCESS_RESULT : new ValidationResult(false, MOVE_ORDER_VIOLATION);
    }

    private ValidationResult validateAppliedCard() {
        Long appliedCardId = doMoveDto.getAppliedCardId();
        Long cardId = getCardId(appliedCardId, gameState);

        return gameState.getCurrentCompetitorState().<CompetitorAccountExtraData>getExtraData().getAvailableCards().containsKey(appliedCardId)
                ? SUCCESS_RESULT : new ValidationResult(false, INVALID_APPLIED_CARD,
                (cardId != null ? cardId.toString() : appliedCardId.toString()));
    }

    private ValidationResult validatePriceOperations() {
        Long appliedCardId = doMoveDto.getAppliedCardId();
        Long cardId = getCardId(appliedCardId, gameState);
        Set<Long> usedShareIds = new HashSet<>();
        for (PriceOperationDto priceOperation : doMoveDto.getPriceOperations()) {
            Long priceOperationId = priceOperation.getPriceOperationId();
            if (priceOperation.getShareId() == null) {
                return new ValidationResult(false, SHARE_NOT_SPECIFIED);
            }
            if (usedShareIds.contains(priceOperation.getShareId())) {
                return new ValidationResult(false, SHARE_DUPLICATED);
            }
            if (!gameVariantInfo.hasShare(priceOperation.getShareId())) {
                return new ValidationResult(false, INVALID_SHARE_ID, priceOperation.getShareId().toString());
            }
            if (!gameVariantInfo.hasCardOperation(cardId, priceOperationId)) {
                return new ValidationResult(false, ValidationError.INVALID_CARD_OPERATION, priceOperationId.toString());
            }
            Long shareId = gameVariantInfo.getShareIdForPriceOperation(cardId, priceOperationId);
            if (shareId != null && !shareId.equals(priceOperation.getShareId())) {
                new ValidationResult(false, INVALID_APPLIED_CARD, cardId.toString());
            }
            usedShareIds.add(priceOperation.getShareId());
        }
        return SUCCESS_RESULT;
    }

    private Long getCardId(Long appliedCardId, GameState gameState) {
        return gameState.getCurrentCompetitorState().<CompetitorAccountExtraData>getExtraData().getCardId(appliedCardId);
    }

    private ValidationResult validateBuySellOperations() {
        for(BuySellDto buySellDto : doMoveDto.getFirstBuySellActions()) {
            if (!gameVariantInfo.hasShare(buySellDto.getShareId())) {
                return new ValidationResult(false, INVALID_SHARE_ID, buySellDto.getShareId().toString());
            }
        }
        for (BuySellDto buySellDto : doMoveDto.getLastBuySellActions()) {
            if (!gameVariantInfo.hasShare(buySellDto.getShareId())) {
                return new ValidationResult(false, INVALID_SHARE_ID, buySellDto.getShareId().toString());
            }
        }
        return SUCCESS_RESULT;
    }
}
