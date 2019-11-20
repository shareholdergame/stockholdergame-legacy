package com.stockholdergame.server.model.game;

import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.*;

/**
 * @author Alexander Savin
 *         Date: 17.5.11 23.15
 */
public enum InvitationStatus implements Descriptable {

    CREATED(INVITATION_STATUS_CREATED),

    ACCEPTED(INVITATION_STATUS_ACCEPTED),

    REJECTED(INVITATION_STATUS_REJECTED),

    EXPIRED(INVITATION_STATUS_EXPIRED),

    CANCELLED(INVITATION_STATUS_CANCELLED);

    private String description;

    InvitationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
