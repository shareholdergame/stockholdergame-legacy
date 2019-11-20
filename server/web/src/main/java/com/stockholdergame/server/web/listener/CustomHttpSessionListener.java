package com.stockholdergame.server.web.listener;

import static com.stockholdergame.server.session.UserSessionUtil.USER_SESSION_DATA;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.services.security.UserSessionTrackingService;
import com.stockholdergame.server.session.UserSessionData;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Alexander Savin
 */
public class CustomHttpSessionListener implements HttpSessionListener {

    private UserSessionTrackingService userSessionTrackingService;

    private static Logger LOGGER = org.apache.log4j.Logger.getLogger(CustomHttpSessionListener.class);

    public void sessionCreated(HttpSessionEvent event) {
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        UserSessionData userSessionData = (UserSessionData) session.getAttribute(USER_SESSION_DATA);
        ServletContext servletContext = event.getSession().getServletContext();
        if (userSessionData != null
                && getUserSessionTrackerService(servletContext).isUserOnline(userSessionData.getUserInfo().getUserName())) {
            getUserSessionTrackerService(servletContext).logClosedSession(userSessionData);
        }
    }

    public UserSessionTrackingService getUserSessionTrackerService(ServletContext servletContext) {
        if (userSessionTrackingService == null) {
            userSessionTrackingService = (UserSessionTrackingService) WebApplicationContextUtils
                    .getWebApplicationContext(servletContext).getBean("userSessionTrackingServiceImpl");
            if (userSessionTrackingService == null) {
                throw new ApplicationException("Bean userSessionTrackingServiceImpl not found in application context.");
            }
        }
        return userSessionTrackingService;
    }
}

