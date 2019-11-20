package com.stockholdergame.server.services.messaging;

import com.stockholdergame.server.model.game.GameEventType;

/**
 * Game event producer.
 */
public interface GameEventProducer {

    <T> void fireEvent(GameEventType eventType, Long senderId, Long recipientId, Long objectId, T body);
}
