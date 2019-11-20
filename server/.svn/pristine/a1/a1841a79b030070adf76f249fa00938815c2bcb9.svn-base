package com.stockholdergame.server.services.messaging.impl;

import com.stockholdergame.server.dao.FriendRequestDao;
import com.stockholdergame.server.dao.GameEventDao;
import com.stockholdergame.server.dao.util.IdentifierHelper;
import com.stockholdergame.server.dto.account.FriendRequestDto;
import com.stockholdergame.server.dto.game.GameEventDto;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.model.account.FriendRequest;
import com.stockholdergame.server.model.game.GameEvent;
import com.stockholdergame.server.model.game.GameEventType;
import com.stockholdergame.server.services.messaging.GameEventContainerService;
import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.session.UserSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Savin
 *         Date: 31.5.12 22.27
 */
@Service
public class GameEventContainerServiceImpl implements GameEventContainerService {

    @Autowired
    private GameEventDao gameEventDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    public Long saveGameEvent(Long gamerId, Long objectId, GameEventType eventType) {
        GameEvent gameEvent = new GameEvent();
        gameEvent.setId(IdentifierHelper.generateLongId());
        gameEvent.setGamerId(gamerId);
        gameEvent.setEventType(eventType.name());
        gameEvent.setObjectId(objectId);
        gameEvent.setCreated(new Date());

        gameEventDao.create(gameEvent);

        return gameEvent.getId();
    }

    public void removeEvents(Long[] gameEventIds) {
        for (Long gameEventId : gameEventIds) {
            GameEvent gameEvent = gameEventDao.findByPrimaryKey(gameEventId);
            if (gameEvent != null) {
                gameEventDao.remove(gameEvent);
            }
        }
    }

    @Override
    public List<GameEventDto> getLastEvents() {
        UserInfo userInfo = UserSessionUtil.getCurrentUser();
        List<GameEvent> gameEvents = gameEventDao.findByUserId(userInfo.getId());
        List<GameEventDto> gameEventDtos = new ArrayList<>();
        Map<GameEventType, Map<Long, GameEventDto>> objectsMap = new HashMap<>();
        for (GameEvent gameEvent : gameEvents) {
            GameEventDto gameEventDto = new GameEventDto();
            gameEventDto.setCreated(gameEvent.getCreated());
            gameEventDto.setEventType(gameEvent.getEventType());
            if (!objectsMap.containsKey(GameEventType.valueOf(gameEvent.getEventType()))) {
                objectsMap.put(GameEventType.valueOf(gameEvent.getEventType()), new HashMap<Long, GameEventDto>());
            }
            objectsMap.get(GameEventType.valueOf(gameEvent.getEventType())).put(gameEvent.getObjectId(), gameEventDto);
            gameEventDtos.add(gameEventDto);
        }
        for (GameEventType userNotificationType : objectsMap.keySet()) {
            if (userNotificationType.equals(GameEventType.FRIEND_REQUEST_RECEIVED)) {
                for (Map.Entry<Long, GameEventDto> entry : objectsMap.get(GameEventType.FRIEND_REQUEST_RECEIVED).entrySet()) {
                    FriendRequest fr = friendRequestDao.findByPrimaryKey(entry.getKey());
                    if (fr != null) {
                        FriendRequestDto friendRequestDto = DtoMapper.map(fr, FriendRequestDto.class);
                        entry.getValue().setData(friendRequestDto);
                    }
                }
            } else if (userNotificationType.equals(GameEventType.FRIEND_REQUEST_STATUS_CHANGED)) {
                for (Map.Entry<Long, GameEventDto> entry : objectsMap.get(GameEventType.FRIEND_REQUEST_STATUS_CHANGED).entrySet()) {
                    FriendRequest fr = friendRequestDao.findByPrimaryKey(entry.getKey());
                    if (fr != null) {
                        FriendRequestDto friendRequestDto = DtoMapper.map(fr, FriendRequestDto.class);
                        entry.getValue().setData(friendRequestDto);
                    }
                }
            } else if (userNotificationType.equals(GameEventType.INVITATION_RECEIVED)) {
                for (Map.Entry<Long, GameEventDto> entry : objectsMap.get(GameEventType.INVITATION_RECEIVED).entrySet()) {

                }
            } else if (userNotificationType.equals(GameEventType.INVITATION_STATUS_CHANGED)) {
                for (Map.Entry<Long, GameEventDto> entry : objectsMap.get(GameEventType.INVITATION_STATUS_CHANGED).entrySet()) {

                }
            }
        }
        return gameEventDtos;
    }
}
