package com.stockholdergame.server.services.event;

import com.stockholdergame.server.model.event.BusinessEvent;
import com.stockholdergame.server.model.event.BusinessEventType;

/**
 * @author Aliaksandr Savin
 */
public class BusinessEventBuilder<T> {

    private BusinessEventType businessEventType;

    private T payload;

    private Long userId;

    private BusinessEventBuilder() {
    }

    public static <T> BusinessEventBuilder<T> initBuilder() {
        return new BusinessEventBuilder<>();
    }

    public BusinessEventBuilder<T> setType(BusinessEventType eventType) {
        this.businessEventType = eventType;
        return this;
    }

    public BusinessEventBuilder<T> setPayload(T payload) {
        this.payload = payload;
        return this;
    }

    public BusinessEventBuilder<T> setInitiatorId(Long userId) {
        this.userId = userId;
        return this;
    }

    public BusinessEvent<T> toEvent() {
        return new BusinessEvent<>(businessEventType, userId, payload);
    }
}
