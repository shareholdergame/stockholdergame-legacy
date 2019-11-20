package com.stockholdergame.server.gamebot.impl;

import com.stockholdergame.server.dto.game.variant.CardDto;
import com.stockholdergame.server.dto.game.variant.CardOperationDto;
import com.stockholdergame.server.gamebot.GameAnalyzer;
import com.stockholdergame.server.gamebot.domain.CardForceMatrix;
import com.stockholdergame.server.gamebot.domain.MoveAdvice;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.model.game.variant.PriceOperationType;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import com.stockholdergame.server.util.collections.Pair;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Component
public class GameAnalyzerImpl implements GameAnalyzer {

    private static Map<Pair<Integer, String>, Float> markMap =
        new HashMap<Pair<Integer, String>, Float>() {
            {
                put(new Pair<>(100, PriceOperationType.ADDITION.getOperationSign()), 2.0f);
                put(new Pair<>(2, PriceOperationType.MULTIPLICATION.getOperationSign()), 1.5f);
                put(new Pair<>(60, PriceOperationType.ADDITION.getOperationSign()), 1.0f);
                put(new Pair<>(50, PriceOperationType.ADDITION.getOperationSign()), 1.0f);
                put(new Pair<>(40, PriceOperationType.ADDITION.getOperationSign()), 0.5f);
                put(new Pair<>(30, PriceOperationType.ADDITION.getOperationSign()), 0.5f);
                put(new Pair<>(2, PriceOperationType.DIVISION.getOperationSign()), -1.5f);
                put(new Pair<>(60, PriceOperationType.SUBTRACTION.getOperationSign()), -1.0f);
                put(new Pair<>(50, PriceOperationType.SUBTRACTION.getOperationSign()), -1.0f);
                put(new Pair<>(40, PriceOperationType.SUBTRACTION.getOperationSign()), -0.5f);
                put(new Pair<>(30, PriceOperationType.SUBTRACTION.getOperationSign()), -0.5f);
                put(new Pair<>(20, PriceOperationType.SUBTRACTION.getOperationSign()), -0.5f);
                put(new Pair<>(10, PriceOperationType.SUBTRACTION.getOperationSign()), -0.5f);
            }
        };

    @Override
    public MoveAdvice analyzePosition(GameState gameState, CardForceMatrix cardForceMatrix) {
        Map<Long, Integer> sharesPrice = gameState.getSharePrices();
        Map<Long, Integer> sharesNumber = gameState.getCurrentCompetitorState().getShareQuantities();
        Map<Long, Pair<Integer, Integer>> sharesSortedByPriceAndNumber = CollectionsUtil.sortMapByValues(mergeShareMaps(sharesPrice, sharesNumber));
        Map<Long, Pair<Integer, Integer>> sharesSortedByPriceAndNumberDesc =
                CollectionsUtil.sortMapByValues(mergeShareMaps(sharesPrice, sharesNumber), true);

        LinkedList<Map.Entry<Long, Pair<Integer, Integer>>> entries =
            new LinkedList<>(sharesSortedByPriceAndNumber.entrySet());
        if (entries.getFirst().getValue().getFirst().equals(entries.getLast().getValue().getFirst())) {
            Map<Long, Float> sharesByForce = CollectionsUtil.sortMapByValues(cardForceMatrix.getSharesSortedByForceRange());
            Map<Long, Float> sharesByForceDesc = CollectionsUtil.sortMapByValues(cardForceMatrix.getSharesSortedByForceRange(), true);
            return new MoveAdvice(sharesByForce.keySet(), sharesByForceDesc.keySet());
        } else {
            return new MoveAdvice(sharesSortedByPriceAndNumber.keySet(), sharesSortedByPriceAndNumberDesc.keySet());
        }
    }

    private Map<Long, Pair<Integer, Integer>> mergeShareMaps(Map<Long, Integer> sharesPrice, Map<Long, Integer> sharesNumber) {
        Map<Long, Pair<Integer, Integer>> mergedMap = new HashMap<>(sharesPrice.size());
        for (Map.Entry<Long, Integer> entry : sharesPrice.entrySet()) {
            mergedMap.put(entry.getKey(), new Pair<>(entry.getValue(), sharesNumber.get(entry.getKey())));
        }
        return mergedMap;
    }

    @Override
    public CardForceMatrix analyzeCards(Collection<CardDto> cards, Set<Long> shareIds) {
        CardForceMatrix cardForceMatrix = new CardForceMatrix(shareIds);
        for (CardDto card : cards) {
            float mark = 0f;
            Set<CardOperationDto> cardOperations = card.getCardOperations();
            for (CardOperationDto cardOperation : cardOperations) {
                if (cardOperation.getShareId() != null) {
                    mark = markMap.get(new Pair<>(cardOperation.getOperandValue(), cardOperation.getOperation()));
                }
            }
            cardForceMatrix.addCard(card, mark);
        }
        return cardForceMatrix;
    }
}
