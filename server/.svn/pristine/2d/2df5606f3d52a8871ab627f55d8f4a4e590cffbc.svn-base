package com.stockholdergame.server.web.security;

import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.session.UserSessionUtil;
import flex.messaging.services.messaging.Subtopic;
import flex.messaging.services.messaging.adapters.ActionScriptAdapter;

/**
 * @author Alexander Savin
 *         Date: 17.3.11 21.36
 */
public class ProtectedMessagingAdapter extends ActionScriptAdapter {

    @Override
    public boolean allowSubscribe(Subtopic subtopic) {
        UserInfo userInfo = UserSessionUtil.getCurrentUser();
        return !(userInfo == null || userInfo.isRemoved() || !userInfo.getSubtopicName().equals(subtopic.getValue()))
                && !(subtopic.containsSubtopicWildcard());
    }
}
