package com.stockholdergame.server.services.account.impl;

import static com.stockholdergame.server.model.account.OperationStatus.CANCELLED;
import static com.stockholdergame.server.model.account.OperationStatus.COMPLETED;
import static com.stockholdergame.server.model.account.OperationStatus.PENDING_VERIFICATION;
import static com.stockholdergame.server.model.account.OperationType.EMAIL_CHANGED;
import static com.stockholdergame.server.model.account.OperationType.PASSWORD_CHANGED;
import static com.stockholdergame.server.model.account.OperationType.STATUS_CHANGED;
import static com.stockholdergame.server.model.account.OperationType.USER_NAME_CHANGED;
import static com.stockholdergame.server.util.AccountUtils.calculateOperationExpirationDate;
import com.stockholdergame.server.dao.AccountOperationDao;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.OperationType;
import com.stockholdergame.server.services.account.AccountOperationsService;
import com.stockholdergame.server.util.security.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 29.4.2010 8.42.11
 */
@Service("accountOperationsService")
@Transactional
public class AccountOperationsServiceImpl implements AccountOperationsService {

    private static final String FAKE_PASSWORD = "********";

    @Autowired
    private AccountOperationDao accountOperationDao;

    public void setAccountOperationDao(AccountOperationDao accountOperationDao) {
        this.accountOperationDao = accountOperationDao;
    }

    public AccountOperation startAccountStatusChangeOperation(Long gamerId,
                                                              AccountStatus oldStatus,
                                                              AccountStatus newStatus) {
        AccountOperation ao = accountOperationDao.findUncompletedOperationByType(gamerId, OperationType.STATUS_CHANGED);
        if (ao != null) {
            throw new BusinessException(BusinessExceptionType.OPERATION_ALREADY_EXISTS,
                OperationType.STATUS_CHANGED.getDescription());
        }
        String verificationCode = generateVerificationCode();
        return createOperationRecord(gamerId, STATUS_CHANGED, String.valueOf(oldStatus.ordinal()),
                String.valueOf(newStatus.ordinal()), calculateOperationExpirationDate(new Date()), verificationCode);
    }

    public String completeOperation(Long gamerId, OperationType operationType, String verificationCode) {
        AccountOperation ao = findUncompletedOperation(gamerId, operationType);
        return completeOperation(verificationCode, ao);
    }

    public void cancelUncompletedOperation(Long gamerId, OperationType operationType) {
        AccountOperation ao = findUncompletedOperation(gamerId, operationType);
        cancelOperation(new Date(), ao);
    }

    public void cancelUncompletedOperations(Long gamerId) {
        List<AccountOperation> operations = findUncompletedOperations(gamerId);
        for (AccountOperation operation : operations) {
            cancelOperation(new Date(), operation);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelUncompletedOperation(Long accountOperationId) {
        AccountOperation ao = accountOperationDao.findByPrimaryKey(accountOperationId);
        cancelOperation(new Date(), ao);
    }

    public void auditUserNameChange(Long gamerId, String oldUserName, String newUserName) {
        createOperationRecord(gamerId, USER_NAME_CHANGED, oldUserName, newUserName, null, null);
    }

    public AccountOperation startEmailChangeOperation(Long gamerId, String oldEmail, String newEmail) {
        AccountOperation operation = accountOperationDao.findUncompletedOperationByType(gamerId,
            OperationType.EMAIL_CHANGED);
        if (operation != null) {
            cancelOperation(new Date(), operation);
        }
        String verificationCode = generateVerificationCode();
        return createOperationRecord(gamerId, EMAIL_CHANGED, oldEmail, newEmail,
                calculateOperationExpirationDate(new Date()), verificationCode);
    }

    public void auditPasswordChange(Long gamerId) {
        createOperationRecord(gamerId, PASSWORD_CHANGED, FAKE_PASSWORD, FAKE_PASSWORD, null, null);
    }

    public List<AccountOperation> findUncompletedOperations(Long gamerId) {
        return accountOperationDao.findUncompletedOperations(gamerId);
    }

    public AccountOperation findUncompletedOperationByType(Long gamerId, OperationType operationType) {
        return accountOperationDao.findUncompletedOperationByType(gamerId, operationType);
    }

    public List<AccountOperation> findUncompletedOperationsWithExpiredTerm(Date expiredDate) {
        return accountOperationDao.findUncompletedOperationsWithExpiredTerm(expiredDate);
    }

    private AccountOperation findUncompletedOperation(Long gamerId, OperationType operationType) {
        AccountOperation ao = accountOperationDao.findUncompletedOperationByType(gamerId, operationType);
        if (ao == null) {
            throw new BusinessException(BusinessExceptionType.OPERATION_NOT_FOUND, operationType.getDescription());
        }
        return ao;
    }

    private void cancelOperation(Date today, AccountOperation accountOperation) {
        accountOperation.setOperationStatus(CANCELLED);
        accountOperation.setCompletionDate(today);
        accountOperationDao.update(accountOperation);
    }

    private AccountOperation createOperationRecord(Long gamerId, OperationType operationType, String oldValue,
                                                   String newValue, Date expirationDate, String verificationCode) {
        AccountOperation aa = createAccountOperation(gamerId, operationType, oldValue, newValue, expirationDate,
                verificationCode);
        accountOperationDao.create(aa);
        return aa;
    }

    private AccountOperation createAccountOperation(Long gamerId, OperationType operationType,
                                                    String oldValue, String newValue, Date expirationDate,
                                                    String verificationCode) {
        Date currTime = new Date();
        AccountOperation aa = new AccountOperation();
        aa.setGamerId(gamerId);
        aa.setOperationType(operationType);
        aa.setOldValue(oldValue);
        aa.setNewValue(newValue);
        aa.setVerificationCode(verificationCode);
        aa.setInitiationDate(currTime);
        if (expirationDate != null) {
            aa.setOperationStatus(PENDING_VERIFICATION);
            aa.setExpirationDate(expirationDate);
        } else {
            aa.setOperationStatus(COMPLETED);
            aa.setCompletionDate(currTime);
        }
        return aa;
    }

    private String completeOperation(String verificationCode, AccountOperation ao) {
        if (ao.getVerificationCode() != null && ao.getVerificationCode().equals(verificationCode)) {
            ao.setOperationStatus(COMPLETED);
            ao.setCompletionDate(new Date());
            accountOperationDao.update(ao);
            return ao.getNewValue();
        } else {
            throw new BusinessException(BusinessExceptionType.INVALID_CONFIRMATION_CODE, verificationCode);
        }
    }

    String generateVerificationCode() {
        return RandomStringGenerator.generate(16);
    }
}
