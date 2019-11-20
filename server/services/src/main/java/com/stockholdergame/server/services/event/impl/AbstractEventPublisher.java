package com.stockholdergame.server.services.event.impl;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author Aliaksandr Savin
 */
public abstract class AbstractEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    protected  <E extends ApplicationEvent> void publishEvent(E event) {
        publisher.publishEvent(event);
    }
}
