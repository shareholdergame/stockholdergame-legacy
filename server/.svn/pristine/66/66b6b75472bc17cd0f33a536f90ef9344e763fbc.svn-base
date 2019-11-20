package com.stockholdergame.server.gamebot.domain;

import com.stockholdergame.server.dto.game.variant.CardDto;
import com.stockholdergame.server.gamecore.GameState;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * game holder.
 */
public class BotGameHolder {

    private GameState gameState;

    private Map<Long, CardDto> cards;

    public BotGameHolder(GameState gameState, Map<Long, CardDto> cards) {
        this.gameState = gameState;
        this.cards = cards;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Collection<CardDto> getCards() {
        return Collections.unmodifiableCollection(cards.values());
    }

    public Long getCompetitorCardIdByCardId(Long cardId) {
        for (Map.Entry<Long, CardDto> entry : cards.entrySet()) {
            if (entry.getValue().getId().equals(cardId)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void removeCard(Long cardId) {
        if (cards.containsKey(cardId)) {
            cards.remove(cardId);
        }
    }
}
