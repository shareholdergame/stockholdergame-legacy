package com.stockholdergame.server.services.security.impl;

import com.stockholdergame.server.dao.UserSessionLogDao;
import com.stockholdergame.server.model.account.UserSessionLog;
import com.stockholdergame.server.model.event.BusinessEventType;
import com.stockholdergame.server.services.event.BusinessEventBuilder;
import com.stockholdergame.server.services.event.impl.AbstractEventPublisher;
import com.stockholdergame.server.services.security.UserSessionTrackingService;
import com.stockholdergame.server.session.UserSessionData;
import com.stockholdergame.server.util.collections.MultiKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
public class UserSessionTrackingServiceImpl extends AbstractEventPublisher implements UserSessionTrackingService {

    private MultiKeyMap<Long, String, String> onlineUsers = new MultiKeyMap<>();

    @Autowired
    private UserSessionLogDao userSessionLogDao;

    @Transactional
    public void logNewSession(UserSessionData userSessionData) {
        String userName = userSessionData.getUserInfo().getUserName();
        if (onlineUsers.containsKey(null, userName)
            && onlineUsers.get(null, userName).equals(userSessionData.getHttpSessionId())) {
            return;
        }

        Date currentTime = new Date();

        Long userId = userSessionData.getUserInfo().getId();
        closeUnclosedSessionLogRecords(userId, currentTime);

        onlineUsers.put(userId, userName, userSessionData.getHttpSessionId());
        UserSessionLog newSession = new UserSessionLog();
        newSession.setGamerId(userId);
        newSession.setStartTime(currentTime);
        newSession.setIpAddress(userSessionData.getUserIPAddress());
        userSessionLogDao.create(newSession);
        userSessionData.setSessionId(newSession.getId());

        publishEvent(BusinessEventBuilder.<String>initBuilder()
            .setInitiatorId(userId).setPayload(userSessionData.getUserIPAddress())
            .setType(BusinessEventType.USER_LOGGED_IN).toEvent());
    }

    @Transactional
    public void logClosedSession(UserSessionData userSessionData) {
        if (!onlineUsers.containsKey(null, userSessionData.getUserInfo().getUserName())) {
            return;
        }
        onlineUsers.remove(userSessionData.getUserInfo().getId(), userSessionData.getUserInfo().getUserName());

        Date currentTime = new Date();

        UserSessionLog lastSession = userSessionLogDao.findByPrimaryKey(userSessionData.getSessionId());
        if (lastSession != null) {
            lastSession.setEndTime(currentTime);
            userSessionLogDao.update(lastSession);
        }
    }

    public boolean isUserOnline(String userName) {
        return onlineUsers.containsKey(null, userName);
    }

    @Override
    public boolean isUserOnline(Long userId) {
        return onlineUsers.containsKey(userId, null);
    }

    @Override
    public int getOnlineUsersCount() {
        return onlineUsers.size();
    }

    @Override
    public Collection<String> getOnlineUserNames(int limit) {
        int i = 0;
        Iterator<String> namesIterator = onlineUsers.iterateByKey2();
        Set<String> userNames = new HashSet<>();
        while (namesIterator.hasNext() && i < limit) {
            userNames.add(namesIterator.next());
        }
        return userNames;
    }

    private void closeUnclosedSessionLogRecords(Long userId, Date currentTime) {
        List<UserSessionLog> userSessionLogs = userSessionLogDao.findUnclosedLogRecords(userId);
        for(UserSessionLog userSessionLog : userSessionLogs) {
            userSessionLog.setEndTime(currentTime);
            userSessionLogDao.update(userSessionLog);
        }
    }
}
