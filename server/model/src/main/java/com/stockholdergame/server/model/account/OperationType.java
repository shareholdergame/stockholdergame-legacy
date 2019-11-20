package com.stockholdergame.server.model.account;

import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.*;

/**
 * @author Alexander Savin
 */
public enum OperationType implements Descriptable {

    STATUS_CHANGED (OPERATION_TYPE_STATUS_CHANGED),

    USER_NAME_CHANGED (OPERATION_TYPE_USER_NAME_CHANGED),

    EMAIL_CHANGED (OPERATION_TYPE_EMAIL_CHANGED),

    PASSWORD_CHANGED (OPERATION_TYPE_PASSWORD_CHANGED);

    private String description;

    OperationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
