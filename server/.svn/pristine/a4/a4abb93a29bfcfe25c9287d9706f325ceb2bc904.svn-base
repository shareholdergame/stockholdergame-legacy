package com.stockholdergame.server.model.game;

import com.stockholdergame.server.gamecore.GameState;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 20.11.2010 23.41.17
 */
public interface GameStateAdapter extends GameState {

    Long  getGameId();

    GameStatus getGameStatus();

    CompetitorAccountAdapter getCurrentCompetitorState();

    Set<CompetitorAccountAdapter> getCompetitorStates();

    Set<Long> getGamerIds();

    Long getGameVariantId();

    boolean isRoundUp();

    int getPriceStep();

    int getMaxPrice();

    CompetitorAccountAdapter getCompetitorState(Long competitorId);
}
