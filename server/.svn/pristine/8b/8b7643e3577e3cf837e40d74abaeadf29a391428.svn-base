package com.stockholdergame.server.gamebot;

import com.stockholdergame.server.dto.game.variant.CardDto;
import com.stockholdergame.server.gamebot.domain.CardForceMatrix;
import com.stockholdergame.server.gamebot.domain.MoveAdvice;
import com.stockholdergame.server.gamecore.GameState;

import java.util.Collection;
import java.util.Set;

/**
 *
 */
public interface GameAnalyzer {

    MoveAdvice analyzePosition(GameState gameState, CardForceMatrix cardForceMatrix);

    CardForceMatrix analyzeCards(Collection<CardDto> cards, Set<Long> shareIds);
}
