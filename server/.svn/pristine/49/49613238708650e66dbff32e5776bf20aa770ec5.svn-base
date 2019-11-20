package com.stockholdergame.server.services.account;

import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.OperationType;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 28.4.2010 22.56.50
 */
public interface AccountOperationsService {

    List<AccountOperation> findUncompletedOperations(Long gamerId);

    AccountOperation findUncompletedOperationByType(Long gamerId, OperationType operationType);

    List<AccountOperation> findUncompletedOperationsWithExpiredTerm(Date expiredDate);

    AccountOperation startAccountStatusChangeOperation(Long gamerId, AccountStatus oldStatus, AccountStatus newStatus);

    AccountOperation startEmailChangeOperation(Long gamerId, String oldEmail, String newEmail);

    void auditUserNameChange(Long gamerId, String oldUserName, String newUserName);

    void auditPasswordChange(Long gamerId);

    String completeOperation(Long gamerId, OperationType operationType, String verificationCode);

    void cancelUncompletedOperations(Long gamerId);

    void cancelUncompletedOperation(Long gamerId, OperationType operationType);

    void cancelUncompletedOperation(Long accountOperationId);
}
