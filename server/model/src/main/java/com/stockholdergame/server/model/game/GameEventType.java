package com.stockholdergame.server.model.game;

/**
 * @author Alexander Savin
 *         Date: 12.1.13 15.22
 */
public enum GameEventType {

    USER_JOINED,

    INVITATION_RECEIVED,

    INVITATION_ACCEPTED,

    INVITATION_REJECTED,

    INVITATION_EXPIRED,

    INVITATION_CANCELLED,

    GAME_STARTED,

    MOVE_DONE,

    GAME_FINISHED,

    INVITATION_STATUS_CHANGED, // todo - remove

    MESSAGE_RECEIVED,

    FRIEND_REQUEST_STATUS_CHANGED,

    GAME_CANCELED,

    GAME_INTERRUPTED,

    FRIEND_REQUEST_RECEIVED
}
