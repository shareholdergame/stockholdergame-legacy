package com.stockholdergame.server.session;

import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.SESSION_NOT_EXIST;
import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.USER_SESSION_DATA_NOT_EXIST;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.FLEX_SESSION_NOT_AVAILABLE;
import static com.stockholdergame.server.localization.MessageHolder.getMessage;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.model.account.GamerAccount;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.stockholdergame.server.services.security.CustomUser;
import flex.messaging.FlexContext;
import flex.messaging.FlexSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Alexander Savin
 */
public final class UserSessionUtil {

    public static final String USER_SESSION_DATA = "userSessionData";

    public static final String UNAUTH_LOCALE_ATTRIBUTE = "unauth_lang";

    private UserSessionUtil() {
    }

    public static UserSessionData initUserSessionData(GamerAccount ga)
        throws NullPointerException {
        SessionWrapper session = getSession();
        if (session != null) {
            UserSessionData userSessionData = (UserSessionData) session.getAttribute(USER_SESSION_DATA);
            if (userSessionData == null) {
                userSessionData = new UserSessionData(getRemoteAddr(), session.getId());
                session.setAttribute(USER_SESSION_DATA, userSessionData);
            }
            userSessionData.setUserInfo(createUserInfo(ga));
            return userSessionData;
        } else {
            throw new ApplicationException(getMessage(FLEX_SESSION_NOT_AVAILABLE, ga.getUserName()));
        }
    }

    private static String getRemoteAddr() {
        HttpServletRequest request = FlexContext.getHttpRequest();
        if (request == null) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            request = attributes.getRequest();
        }
        return request.getRemoteAddr();
    }

    public static UserSessionData getCurrentSession() {
        SessionWrapper session = getSession();
        if (session == null) {
            throw new ApplicationException(getMessage(SESSION_NOT_EXIST));
        }
        return (UserSessionData) session.getAttribute(USER_SESSION_DATA);
    }

    public static UserInfo getCurrentUser() {
        UserSessionData userSessionData = getCurrentSession();
        if (userSessionData == null) {
            throw new ApplicationException(getMessage(USER_SESSION_DATA_NOT_EXIST));
        }
        return userSessionData.getUserInfo();
    }

    public static Locale getUserLocale() {
        UserSessionData userSessionData = getCurrentSession();
        if (userSessionData != null) {
            return userSessionData.getUserInfo().getLocale();
        } else if (getSession() != null && getSession().getAttribute(UNAUTH_LOCALE_ATTRIBUTE) != null) {
            return (Locale) getSession().getAttribute(UNAUTH_LOCALE_ATTRIBUTE);
        } else {
            return LocaleRegistry.getDefaultLocale();
        }
    }

    public static UserInfo createUserInfo(GamerAccount ga) {
        return new UserInfo(ga.getId(), ga.getUserName(), ga.getEmail(), ga.getLocale(),
                ga.getStatus(), ga.getSubtopicName());
    }

    public static SessionWrapper getSession() {
        final FlexSession flexSession = FlexContext.getFlexSession();
        if (flexSession != null) {
            return new SessionWrapper() {

                @Override
                public String getId() {
                    return flexSession.getId();
                }

                @Override
                public Object getAttribute(String name) {
                    return flexSession.getAttribute(name);
                }

                @Override
                public void setAttribute(String name, Object value) {
                    flexSession.setAttribute(name, value);
                }

                @Override
                public void removeAttribute(String name) {
                    flexSession.removeAttribute(name);
                }
            };
        } else {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String remoteAddr = request.getRemoteAddr();
            final HttpSession session = request.getSession();
            SessionWrapper sessionWrapper = new HttpSessionWrapper(session);
            if (session.getAttribute(USER_SESSION_DATA) == null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof OAuth2Authentication) {
                    OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
                    CustomUser customUser = (CustomUser) oAuth2Authentication.getUserAuthentication().getPrincipal();
                    UserSessionData userSessionData = new UserSessionData(remoteAddr, session.getId());
                    userSessionData.setUserInfo(customUser.getUserInfo());
                    sessionWrapper.setAttribute(USER_SESSION_DATA, userSessionData);
                }
            }
            return sessionWrapper;
        }
    }

    public static void clearSessionData() {
        SessionWrapper session = getSession();
        if (session != null) {
            session.removeAttribute(USER_SESSION_DATA);
        }
    }

    public interface SessionWrapper {
        String getId();

        Object getAttribute(String name);

        void setAttribute(String name, Object value);

        void removeAttribute(String name);
    }

    static class HttpSessionWrapper implements SessionWrapper {
        HttpSession httpSession;

        HttpSessionWrapper(HttpSession httpSession) {
            this.httpSession = httpSession;
        }

        @Override
        public String getId() {
            return httpSession.getId();
        }

        @Override
        public Object getAttribute(String name) {
            return httpSession.getAttribute(name);
        }

        @Override
        public void setAttribute(String name, Object value) {
            httpSession.setAttribute(name, value);
        }

        @Override
        public void removeAttribute(String name) {
            httpSession.removeAttribute(name);
        }
    }
}
