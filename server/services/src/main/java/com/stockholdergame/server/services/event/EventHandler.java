package com.stockholdergame.server.services.event;

/**
 * @author Aliaksandr Savin
 */
public interface EventHandler<T> {

    void handle(Long userId, T payload);
}
