package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountOperationDataProvider;
import com.stockholdergame.server.services.account.AccountLifecycleService;
import com.stockholdergame.server.services.account.AccountOperationsService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.easymock.classextension.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Alexander Savin
 *         Date: 30.4.2010 8.57.09
 */
@Test
public class AccountAdministrationServiceTest {

    private GamerAccountDao gamerAccountDao;

    private AccountLifecycleService accountLifecycleService;

    private AccountOperationsService accountOperationsService;

    private AccountAdministrationServiceImpl accountAdministrationService;

    @BeforeMethod
    private void setUp() {
        accountAdministrationService = new AccountAdministrationServiceImpl();
        gamerAccountDao = EasyMock.createMock(GamerAccountDao.class);
        accountLifecycleService = EasyMock.createMock(AccountLifecycleService.class);
        accountOperationsService = EasyMock.createMock(AccountOperationsService.class);
        accountAdministrationService.setGamerAccountDao(gamerAccountDao);
        accountAdministrationService.setAccountLifecycleService(accountLifecycleService);
        accountAdministrationService.setAccountOperationsService(accountOperationsService);
    }

    public void testCancelOperationsWithExpiredTerm() {
        List<AccountOperation> accountOperations = Arrays.asList(AccountOperationDataProvider.EMAIL_CHNG_OP_EXPIRED,
            AccountOperationDataProvider.STAT_GHNG_NEW_ACT_OP, AccountOperationDataProvider.STAT_GHNG_ACT_RM_OP);

        EasyMock.expect(accountOperationsService.findUncompletedOperationsWithExpiredTerm(EasyMock.<Date>anyObject()))
            .andReturn(accountOperations);
        accountLifecycleService.removeAccount(EasyMock.anyLong());
        accountOperationsService.cancelUncompletedOperation(EasyMock.anyLong());
        EasyMock.expectLastCall().times(2);

        EasyMock.replay(accountOperationsService, accountLifecycleService);
        accountAdministrationService.cancelOperationsWithExpiredTerm();
        EasyMock.verify(accountOperationsService, accountLifecycleService);
    }

    public void testTransferRemovedToRemovedCompletely() {
        EasyMock.expect(gamerAccountDao.findRemovedGamerIdsWithExpiredTerm(EasyMock.<Date>anyObject()))
            .andReturn(Arrays.asList(1L, 2L));
        accountLifecycleService.removeAccount(1L);
        accountLifecycleService.removeAccount(2L);

        EasyMock.replay(gamerAccountDao, accountLifecycleService);
        accountAdministrationService.transferRemovedToRemovedCompletely();
        EasyMock.verify(gamerAccountDao, accountLifecycleService);
    }
}
