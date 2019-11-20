package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.AccountOperationDao;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.OperationType;
import org.easymock.classextension.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.stockholdergame.server.model.account.AccountOperationDataProvider.STAT_GHNG_NEW_ACT_OP;
import static com.stockholdergame.server.model.account.GamerAccountDataProvider.USER_TEST_ID;

@Test
public class AccountOperationsServiceTest {

    private AccountOperationDao accountOperationDao;

    private AccountOperationsServiceImpl accountOperationsService;

    @BeforeMethod
    private void setUp() {
        accountOperationsService = new AccountOperationsServiceImpl();
        accountOperationDao = EasyMock.createMock(AccountOperationDao.class);
        accountOperationsService.setAccountOperationDao(accountOperationDao);
    }

    public void testStartAccountStatusChangeOperation() {
        EasyMock.expect(accountOperationDao.findUncompletedOperationByType(USER_TEST_ID, OperationType.STATUS_CHANGED))
            .andReturn(null);
        accountOperationDao.create(EasyMock.<AccountOperation>anyObject());

        EasyMock.replay(accountOperationDao);
        accountOperationsService.startAccountStatusChangeOperation(USER_TEST_ID,
            AccountStatus.NEW, AccountStatus.ACTIVE);
        EasyMock.verify(accountOperationDao);
    }

    @Test(expectedExceptions = BusinessException.class)
    public void testStartAccountStatusChangeOperationAlreadyExists() {
        EasyMock.expect(accountOperationDao.findUncompletedOperationByType(USER_TEST_ID, OperationType.STATUS_CHANGED))
            .andReturn(STAT_GHNG_NEW_ACT_OP);

        EasyMock.replay(accountOperationDao);
        accountOperationsService.startAccountStatusChangeOperation(USER_TEST_ID,
            AccountStatus.NEW, AccountStatus.ACTIVE);
        EasyMock.verify(accountOperationDao);
    }
}
