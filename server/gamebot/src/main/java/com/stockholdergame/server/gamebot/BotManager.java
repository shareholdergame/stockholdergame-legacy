package com.stockholdergame.server.gamebot;

/**
 *
 */
public interface BotManager {

    void offerGame(Long[] nonOfferedGameVariants);

    void wakeUp();
}
