package com.stockholdergame.server.services.messaging;

/**
 * @author Alexander Savin
 *         Date: 16.3.11 8.41
 */
public interface MessagingService {

    <T> void send(Long gamerId, T notificationBody);
}
