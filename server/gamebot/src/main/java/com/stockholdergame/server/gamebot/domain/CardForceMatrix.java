package com.stockholdergame.server.gamebot.domain;

import com.stockholdergame.server.dto.game.variant.CardDto;
import com.stockholdergame.server.dto.game.variant.CardOperationDto;
import com.stockholdergame.server.gamebot.BotApplicationException;
import com.stockholdergame.server.util.collections.CollectionsUtil;
import com.stockholdergame.server.util.collections.Pair;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class CardForceMatrix {

    private Set<Long> shareIds;

    private Map<Pair<Long, Long>, CardForce> shareCombinations = new HashMap<>();

    private Map<Long, Float[]> totalForce = new HashMap<>(4);

    public CardForceMatrix(Set<Long> shareIds) {
        Validate.notEmpty(shareIds);
        this.shareIds = shareIds;
    }

    public Set<Long> getShareIds() {
        return Collections.unmodifiableSet(shareIds);
    }

    public void addCard(CardDto card, float force) {
        Long shareId = detectShareId(card);

        if (shareId == null) {
            throw new BotApplicationException("Invalid card. Default share not found.");
        }

        CardForce cardForce = new CardForce(card, force);
        for (Long shareId1 : shareIds) {
            if (shareId.equals(shareId1)) {
                continue;
            }
            shareCombinations.put(new Pair<>(force > 0 ? shareId : shareId1, force > 0 ? shareId1 : shareId), cardForce);
        }

        if (!totalForce.containsKey(shareId)) {
            totalForce.put(shareId, new Float[]{0f, 0f});
        }
        totalForce.get(shareId)[force < 0 ? 0 : 1] =+ force;
    }

    private Long detectShareId(CardDto cardDto) {
        Set<CardOperationDto> cardOperations = cardDto.getCardOperations();
        for (CardOperationDto cardOperationDto : cardOperations) {
            Long shareId = cardOperationDto.getShareId();
            if (shareId != null) {
                return shareId;
            }
        }
        return null;
    }

    public Map<Long, Float> getSharesSortedByForceRange() {
        Map<Long, Float> forceRangeMap = new HashMap<>(totalForce.size());
        for (Map.Entry<Long, Float[]> entry : totalForce.entrySet()) {
            forceRangeMap.put(entry.getKey(), entry.getValue()[0] - (entry.getValue()[1]));
        }
        return CollectionsUtil.sortMapByValues(forceRangeMap);
    }

    public Map<Pair<Long, Long>, CardForce> getShareCombinations() {
        return Collections.unmodifiableMap(shareCombinations);
    }

    public static class CardForce {

        private float cardForce;

        private CardDto cardDto;

        public CardForce(CardDto cardDto, float cardForce) {
            this.cardDto = cardDto;
            this.cardForce = cardForce;
        }

        public float getCardForce() {
            return cardForce;
        }

        public CardDto getCardDto() {
            return cardDto;
        }
    }
}
