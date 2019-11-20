package com.stockholdergame.server.model.account;

import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.*;

/**
 * @author Alexander Savin
 */
public enum OperationStatus implements Descriptable {

    PENDING_VERIFICATION (OPERATION_STATUS_VERIFICATION_PENDING),

    PENDING_PAYMENT (OPERATION_STATUS_PAYMENT_PENDING),

    COMPLETED (OPERATION_STATUS_COMPLETED),

    CANCELLED (OPERATION_STATUS_CANCELLED);

    private String description;

    OperationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
