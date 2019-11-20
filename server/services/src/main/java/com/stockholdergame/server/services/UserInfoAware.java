package com.stockholdergame.server.services;

import com.stockholdergame.server.services.event.impl.AbstractEventPublisher;
import com.stockholdergame.server.services.security.UserSessionTrackingService;
import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.session.UserSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alexander Savin
 *         Date: 15.10.2010 21.47.43
 */
public abstract class UserInfoAware extends AbstractEventPublisher {

    @Autowired
    protected UserSessionTrackingService userSessionTrackingService;

    protected boolean isUserOnline(String userName) {
        return userSessionTrackingService.isUserOnline(userName);
    }

    protected boolean isUserOnline(Long userId) {
        return userSessionTrackingService.isUserOnline(userId);
    }

    protected UserInfo getCurrentUser() {
        return UserSessionUtil.getCurrentUser();
    }
}
