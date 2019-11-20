package com.stockholdergame.server.dto.game.event;

import com.stockholdergame.server.model.game.GameEventType;

import java.io.Serializable;

/**
 * @author Alexander Savin
 *         Date: 16.3.11 23.28
 */
public class UserNotification<T> implements Serializable {

    private static final long serialVersionUID = -5703754932892792123L;

    private Long gameEventId;

    private GameEventType type;

    private T body;

    public UserNotification() {
    }

    public UserNotification(Long gameEventId, GameEventType type, T body) {
        this.gameEventId = gameEventId;
        this.type = type;
        this.body = body;
    }

    public GameEventType getType() {
        return type;
    }

    public T getBody() {
        return body;
    }

    public Long getGameEventId() {
        return gameEventId;
    }

    public void setGameEventId(Long gameEventId) {
        this.gameEventId = gameEventId;
    }

    public void setType(GameEventType type) {
        this.type = type;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
