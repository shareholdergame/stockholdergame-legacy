package com.stockholdergame.server.model.game;

import com.stockholdergame.server.gamecore.CompetitorAccount;
import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 20.11.2010 23.41.56
 */
public interface CompetitorAccountAdapter extends CompetitorAccount {

    Long getCompetitorId();

    boolean isCurrent();

    boolean isBot();

    Set<Long> getAvailableCards();

    Long getGamerId();

    Long getCardId(Long competitorCardId);
}
