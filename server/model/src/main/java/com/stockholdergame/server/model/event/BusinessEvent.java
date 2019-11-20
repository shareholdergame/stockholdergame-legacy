package com.stockholdergame.server.model.event;

import org.springframework.context.ApplicationEvent;

/**
 * Business event object.
 */
public class BusinessEvent<T> extends ApplicationEvent {

    private static final long serialVersionUID = 366386254415359436L;

    private BusinessEventType type;

    private Long eventInitiatorUserId;

    private T payload;

    public BusinessEvent(BusinessEventType type, Long eventInitiatorUserId, T payload) {
        super(payload);
        this.type = type;
        this.eventInitiatorUserId = eventInitiatorUserId;
        this.payload = payload;
    }

    public BusinessEventType getType() {
        return type;
    }

    public Long getEventInitiatorUserId() {
        return eventInitiatorUserId;
    }

    public T getPayload() {
        return payload;
    }
}
