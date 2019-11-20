package com.stockholdergame.server.model.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CompetitorAccountExtraData {

    private Long competitorId;

    private Long gamerId;

    private String userName;

    private String subtopicName;

    private boolean bot;

    private Map<Long, Long> availableCardsMap = new HashMap<Long, Long>();

    public Long getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(Long competitorId) {
        this.competitorId = competitorId;
    }

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    public Map<Long, Long> getAvailableCards() {
        return Collections.unmodifiableMap(availableCardsMap);
    }

    public void setAvailableCardsMap(Map<Long, Long> availableCardsMap) {
        this.availableCardsMap = availableCardsMap;
    }

    public Long getCardId(Long competitorCardId) {
        return availableCardsMap.get(competitorCardId);
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public void removeCard(Long competitorCardId) {
        this.availableCardsMap.remove(competitorCardId);
    }
}
