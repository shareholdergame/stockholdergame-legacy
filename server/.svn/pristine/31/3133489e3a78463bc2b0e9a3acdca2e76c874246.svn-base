package com.stockholdergame.server.model.account;

import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.*;

/**
 * @author Alexander Savin
 *         Date: 18.6.11 17.50
 */
public enum FriendRequestStatus implements Descriptable {

    CREATED(FRIEND_STATUS_CREATED),

    CONFIRMED(FRIEND_STATUS_CONFIRMED),

    REJECTED(FRIEND_STATUS_REJECTED),

    CANCELLED(FRIEND_STATUS_CANCELLED);

    private String description;

    FriendRequestStatus(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
