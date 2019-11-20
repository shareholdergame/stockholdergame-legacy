package com.stockholdergame.server.services.messaging;

import com.stockholdergame.server.dto.game.GameEventDto;
import com.stockholdergame.server.model.game.GameEventType;

import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 31.5.12 22.25
 */
public interface GameEventContainerService {

    Long saveGameEvent(Long gamerId, Long objectId, GameEventType userNotificationType);

    void removeEvents(Long[] gameEventIds);

    List<GameEventDto> getLastEvents();
}
