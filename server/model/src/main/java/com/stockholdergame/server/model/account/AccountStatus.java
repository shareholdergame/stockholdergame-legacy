package com.stockholdergame.server.model.account;

import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.*;

/**
 * @author Alexander Savin
 */
public enum AccountStatus implements Descriptable {

    NEW (ACCOUNT_STATUS_NEW),

    ACTIVE (ACCOUNT_STATUS_ACTIVE),

    REMOVED (ACCOUNT_STATUS_REMOVED),

    REMOVED_COMPLETELY (ACCOUNT_STATUS_REMOVED_COMPLETELY);

    private String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
