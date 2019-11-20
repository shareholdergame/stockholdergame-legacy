package com.stockholdergame.server.model.account;

import java.util.Date;

public final class AccountOperationDataProvider {

    public static final AccountOperation EMAIL_CHNG_OP_EXPIRED = createAccountOperation(OperationType.EMAIL_CHANGED,
        true, null, null);

    public static final AccountOperation STAT_GHNG_NEW_ACT_OP = createAccountOperation(OperationType.STATUS_CHANGED,
        true,
        String.valueOf(AccountStatus.NEW.ordinal()), String.valueOf(AccountStatus.ACTIVE.ordinal()));

    public static final AccountOperation STAT_GHNG_ACT_RM_OP = createAccountOperation(OperationType.STATUS_CHANGED,
        true,
        String.valueOf(AccountStatus.ACTIVE.ordinal()), String.valueOf(AccountStatus.REMOVED.ordinal()));

    private static AccountOperation createAccountOperation(OperationType operationType, boolean expired,
                                                           String oldValue, String newValue) {
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOldValue(oldValue);
        accountOperation.setNewValue(newValue);
        accountOperation.setOperationType(operationType);
        accountOperation.setExpirationDate(new Date());
        return accountOperation;
    }

    private AccountOperationDataProvider() {
    }
}
