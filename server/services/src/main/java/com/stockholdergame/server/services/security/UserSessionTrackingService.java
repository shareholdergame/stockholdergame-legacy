package com.stockholdergame.server.services.security;

import com.stockholdergame.server.session.UserSessionData;

import java.util.Collection;

/**
 *
 */
public interface UserSessionTrackingService {

    void logNewSession(UserSessionData userSessionData);

    void logClosedSession(UserSessionData userSessionData);

    boolean isUserOnline(String userName);

    boolean isUserOnline(Long userId);

    int getOnlineUsersCount();

    Collection<String> getOnlineUserNames(int limit);
}
