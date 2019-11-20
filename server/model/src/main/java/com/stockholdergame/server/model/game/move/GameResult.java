package com.stockholdergame.server.model.game.move;

/**
 * @author Alexander Savin
 *         Date: 11.1.11 8.50
 */
public interface GameResult {

    Long getWinnerId();

    Integer getTotalFunds(Long competitorId);
}
