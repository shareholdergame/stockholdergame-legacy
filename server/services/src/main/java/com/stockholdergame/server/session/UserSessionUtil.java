package com.stockholdergame.server.session;

import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.SESSION_NOT_EXIST;
import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.USER_SESSION_DATA_NOT_EXIST;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.FLEX_SESSION_NOT_AVAILABLE;
import static com.stockholdergame.server.localization.MessageHolder.getMessage;
import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.model.account.GamerAccount;

import java.util.Locale;
import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

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
        FlexSession session = getFlexSession();
        if (session != null) {
            UserSessionData userSessionData = (UserSessionData) session.getAttribute(USER_SESSION_DATA);
            if (userSessionData == null) {
                userSessionData = new UserSessionData(FlexContext.getHttpRequest().getRemoteAddr(), session.getId());
                session.setAttribute(USER_SESSION_DATA, userSessionData);
            }
            userSessionData.setUserInfo(createUserInfo(ga));
            return userSessionData;
        } else {
            throw new ApplicationException(getMessage(FLEX_SESSION_NOT_AVAILABLE, ga.getUserName()));
        }
    }

    public static String getHttpSessionId() {
        FlexSession session = getFlexSession();
        return session.getId();
    }

    public static UserSessionData getCurrentSession() {
        FlexSession session = getFlexSession();
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
        } else if (getFlexSession() != null && getFlexSession().getAttribute(UNAUTH_LOCALE_ATTRIBUTE) != null) {
            return (Locale) getFlexSession().getAttribute(UNAUTH_LOCALE_ATTRIBUTE);
        } else {
            return LocaleRegistry.getDefaultLocale();
        }
    }

    private static UserInfo createUserInfo(GamerAccount ga) {
        return new UserInfo(ga.getId(), ga.getUserName(), ga.getEmail(), ga.getLocale(),
                ga.getStatus(), ga.getSubtopicName());
    }

    public static FlexSession getFlexSession() {
        return FlexContext.getFlexSession();
    }

    public static void clearSessionData() {
        FlexSession session = getFlexSession();
        if (session != null) {
            session.removeAttribute(USER_SESSION_DATA);
        }
    }
}
