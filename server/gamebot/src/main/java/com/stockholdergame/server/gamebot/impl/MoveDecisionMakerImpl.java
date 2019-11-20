package com.stockholdergame.server.gamebot.impl;

import com.stockholdergame.server.dto.game.variant.CardDto;
import com.stockholdergame.server.dto.game.variant.CardOperationDto;
import com.stockholdergame.server.gamebot.BotApplicationException;
import com.stockholdergame.server.gamebot.MoveDecisionMaker;
import com.stockholdergame.server.gamebot.domain.BotGameHolder;
import com.stockholdergame.server.gamebot.domain.CardForceMatrix;
import com.stockholdergame.server.gamebot.domain.MoveAdvice;
import com.stockholdergame.server.gamebot.domain.MoveDecision;
import com.stockholdergame.server.gamecore.model.math.ArithmeticOperation;
import com.stockholdergame.server.model.game.variant.PriceOperationType;
import com.stockholdergame.server.util.collections.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Component
public class MoveDecisionMakerImpl implements MoveDecisionMaker {

    private enum GamePart {
        DEBUT(1.5f),
        MIDDLEGAME(2.0f),
        ENDGAME(2.0f);

        private float maxCardForce;

        GamePart(float maxCardForce) {
            this.maxCardForce = maxCardForce;
        }

        private float getMaxCardForce() {
            return maxCardForce;
        }
    }

    @Override
    public MoveDecision makeMoveDecision(MoveAdvice advice, CardForceMatrix cardForceMatrix, BotGameHolder botGameHolder) {
        int currentMoveNumber = botGameHolder.getGameState().getCurrentMoveNumber();
        GamePart currentGamePart = detectGamePart(currentMoveNumber, botGameHolder.getGameState().getMovesQuantity());
        float maxCardForce = currentGamePart.getMaxCardForce();

        Shares shares = detectAvailableSharesPair(cardForceMatrix, advice, maxCardForce);
        if (shares == null && maxCardForce < 2.0f) {
            shares = detectAvailableSharesPair(cardForceMatrix, advice, 2.0f);
            if (shares == null) {
                throw new BotApplicationException("Can't find available share pair");
            }
        }
        Map<Long, Float> firstStepShares = new HashMap<>();
        for (Long shareId : cardForceMatrix.getShareIds()) {
            firstStepShares.put(shareId, shareId.equals(shares.getIncreasedShare())
                    ? 1f
                    : botGameHolder.getGameState().getCurrentCompetitorState().getShareQuantity(shareId) > 10 ? -0.8f : -1f);
        }

        Map<Long, Long> shareOperationMap = new HashMap<>();
        CardDto card = shares.getCardDto();
        Set<CardOperationDto> cardOperations = card.getCardOperations();
        LinkedList<Long> otherShares = new LinkedList<>(Arrays.asList(shares.getOtherDecreasedShares()));
        for (CardOperationDto cardOperation : cardOperations) {
            Long priceOperationId = cardOperation.getPriceOperationId();
            if (isIncreasedOperation(cardOperation.getOperation())) {
                shareOperationMap.put(priceOperationId, shares.getIncreasedShare());
            } else if (shareOperationMap.containsValue(shares.getDecreasedShare())) {
                shareOperationMap.put(priceOperationId, otherShares.removeFirst());
            } else {
                shareOperationMap.put(priceOperationId, shares.getDecreasedShare());
            }
        }

        Map<Long, Float> secondStepShares = new HashMap<>();
        for (Long shareId : cardForceMatrix.getShareIds()) {
            secondStepShares.put(shareId, shareId.equals(shares.getIncreasedShare())
                ? (botGameHolder.getGameState().getCurrentCompetitorState().getShareQuantity(shareId) > 10 ?  -0.8f : -1f)
                : shareId.equals(shares.getDecreasedShare()) ? 0.7f : 0.15f);
        }

        return new MoveDecision(firstStepShares, shares.getCardDto(), shareOperationMap, secondStepShares);
    }

    private boolean isIncreasedOperation(String operation) {
        return PriceOperationType.signOf(operation).getOperation().equals(ArithmeticOperation.ADDITION)
                || PriceOperationType.signOf(operation).getOperation().equals(ArithmeticOperation.MULTIPLICATION);
    }

    private Shares detectAvailableSharesPair(CardForceMatrix cardForceMatrix, MoveAdvice advice, float maxCardForce) {
        Map<Pair<Long, Long>, CardForceMatrix.CardForce> shareCombinations = cardForceMatrix.getShareCombinations();
        for (Long shareId : advice.getSharesToIncrease()) {
            for (Long shareId1 : advice.getSharesToDecrease()) {
                Pair shareCombinationKey = new Pair<>(shareId, shareId1);
                if (shareCombinations.containsKey(shareCombinationKey)) {
                    CardForceMatrix.CardForce cardForce = shareCombinations.get(shareCombinationKey);
                    if (cardForce.getCardForce() <= maxCardForce) {
                        Set<Long> shares = new HashSet<>(cardForceMatrix.getShareIds());
                        shares.remove(shareId);
                        shares.remove(shareId1);
                        return new Shares(cardForce.getCardDto(), shareId, shareId1, shares.toArray(new Long[shares.size()]));
                    }
                }
            }
        }

        return null;
    }

    private GamePart detectGamePart(int currentMoveNumber, int movesQuantity) {
        return currentMoveNumber < 3 ? GamePart.DEBUT : movesQuantity - currentMoveNumber < 3 ? GamePart.ENDGAME : GamePart.MIDDLEGAME;
    }

    private static class Shares {

        private CardDto cardDto;

        private Long increasedShare;

        private Long decreasedShare;

        private Long[] otherDecreasedShares;

        public Shares(CardDto cardDto, Long increasedShare, Long decreasedShare, Long[] otherDecreasedShares) {
            this.cardDto = cardDto;
            this.increasedShare = increasedShare;
            this.decreasedShare = decreasedShare;
            this.otherDecreasedShares = otherDecreasedShares;
        }

        public CardDto getCardDto() {
            return cardDto;
        }

        public Long getIncreasedShare() {
            return increasedShare;
        }

        public Long getDecreasedShare() {
            return decreasedShare;
        }

        public Long[] getOtherDecreasedShares() {
            return otherDecreasedShares;
        }
    }
}
