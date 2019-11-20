package com.stockholdergame.server.web.security;

import com.stockholdergame.server.services.security.UserSessionTrackingService;
import com.stockholdergame.server.session.UserSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Savin
 *         Date: 16.8.11 23.53
 */
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private UserSessionTrackingService userSessionTrackingService;

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (UserSessionUtil.getCurrentSession() != null) {
            userSessionTrackingService.logClosedSession(UserSessionUtil.getCurrentSession());
        }
        UserSessionUtil.clearSessionData();
        SecurityContextHolder.clearContext();
    }
}
