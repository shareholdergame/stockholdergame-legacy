package com.stockholdergame.server.model.event;

/**
 * Business event type enum.
 */
public enum BusinessEventType {

    USER_LOGGED_IN,

    ACCOUNT_CREATED,

    INVITATION_CREATED,

    USER_JOINED,

    INVITATION_STATUS_CHANGED,

    START_GAMES,

    SWITCH_MOVE_ORDER,

    UNREAD_CHAT_NOTIFICATION,

    PLAY_BOT;
}
