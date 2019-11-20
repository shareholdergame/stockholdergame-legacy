package com.stockholdergame.server.services.messaging.impl;

import com.stockholdergame.server.dto.game.event.UserNotification;
import com.stockholdergame.server.model.game.GameEventType;
import com.stockholdergame.server.services.UserInfoAware;
import com.stockholdergame.server.services.messaging.GameEventProducer;
import com.stockholdergame.server.services.messaging.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEventProducerImpl extends UserInfoAware implements GameEventProducer {

    @Autowired
    private MessagingService messagingService;

    @Override
    public <T> void fireEvent(GameEventType eventType, Long senderId, Long recipientId, Long objectId, T body) {
        UserNotification<T> notification = new UserNotification<T>(null, eventType, body);
        messagingService.send(recipientId, notification);
    }
}
