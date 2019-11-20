package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountOperationDataProvider;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.GamerAccountDataProvider;
import com.stockholdergame.server.model.account.OperationType;
import com.stockholdergame.server.services.account.AccountOperationsService;
import com.stockholdergame.server.services.account.UserActivityService;
import com.stockholdergame.server.services.mail.MailPreparationService;
import org.easymock.classextension.EasyMock;
import org.springframework.context.ApplicationEventPublisher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.stockholdergame.server.model.account.GamerAccountDataProvider.USER_TEST_EMAIL;
import static com.stockholdergame.server.model.account.GamerAccountDataProvider.USER_TEST_ID;
import static com.stockholdergame.server.model.account.GamerAccountDataProvider.USER_TEST_NAME;

@Test
public class AccountLifecycleServiceTest {

    private GamerAccountDao gamerAccountDao;

    private AccountOperationsService accountOperationsService;

    private AccountLifecycleServiceImpl accountLifecycleService;

    private MailPreparationService mailPreparationService;

    private UserActivityService userActivityService;

    private ApplicationEventPublisher applicationEventPublisher;

    public static final GamerAccount TEST_ACCOUNT_NEW = GamerAccountDataProvider.createTestAccountNew();

    public static final GamerAccount TEST_ACCOUNT_REMOVED = GamerAccountDataProvider.createTestAccountRemoved();

    public static final GamerAccount TEST_ACCOUNT_REMOVED_COMPLETELY =
        GamerAccountDataProvider.createTestAccountRemovedCompletely();

    @BeforeMethod
    private void setUp() {
        accountLifecycleService = new AccountLifecycleServiceImpl();
        gamerAccountDao = EasyMock.createMock(GamerAccountDao.class);
        accountOperationsService = EasyMock.createMock(AccountOperationsService.class);
        mailPreparationService = EasyMock.createMock(MailPreparationService.class);
        userActivityService = EasyMock.createMock(UserActivityService.class);
        applicationEventPublisher = EasyMock.createMock(ApplicationEventPublisher.class);
        accountLifecycleService.setGamerAccountDao(gamerAccountDao);
        accountLifecycleService.setOperationsService(accountOperationsService);
        accountLifecycleService.setMailPreparationService(mailPreparationService);
        accountLifecycleService.setUserActivityService(userActivityService);
        accountLifecycleService.setApplicationEventPublisher(applicationEventPublisher);
    }

    /*@Test(dataProvider = "getData4testCreateNewAccount")
    public void testCreateNewAccount(String language, Locale locale) {
        EasyMock.expect(gamerAccountDao.findByUserName(USER_TEST_NAME)).andReturn(null);
        EasyMock.expect(gamerAccountDao.findByEmail(USER_TEST_EMAIL)).andReturn(null);
        gamerAccountDao.create(EasyMock.<GamerAccount>anyObject());
        EasyMock.expect(accountOperationsService.startAccountStatusChangeOperation(EasyMock.anyLong(),
            EasyMock.<AccountStatus>anyObject(), EasyMock.<AccountStatus>anyObject())).andReturn(
            STAT_GHNG_NEW_ACT_OP);
        mailPreparationService.prepareVerificationMessageForAccountOperation(USER_TEST_NAME, USER_TEST_EMAIL,
            locale, STAT_GHNG_NEW_ACT_OP);

        applicationEventPublisher.publishEvent(EasyMock.<ApplicationEvent>anyObject());

        EasyMock.replay(gamerAccountDao, accountOperationsService, mailPreparationService, applicationEventPublisher);
        accountLifecycleService.createNewAccount(USER_TEST_NAME, USER_TEST_EMAIL, USER_TEST_PWD,
            language);
        EasyMock.verify(gamerAccountDao, accountOperationsService, mailPreparationService, applicationEventPublisher);
    }*/

    @DataProvider
    private Object[][] getData4testCreateNewAccount() {
        return new Object[][] {
            {LocaleRegistry.getDefaultLocale().getLanguage(), LocaleRegistry.getDefaultLocale()},
            {null, LocaleRegistry.getDefaultLocale()}
        };
    }

    @Test(expectedExceptions = BusinessException.class, dataProvider = "getData4testCreateNewAccountUserAlreadyExists")
    public void testCreateNewAccountUserAlreadyExists(String userName, String email) {
        if (email == null) {
            EasyMock.expect(gamerAccountDao.findByUserName(userName)).andReturn(TEST_ACCOUNT_NEW);
        } else {
            EasyMock.expect(gamerAccountDao.findByUserName(userName)).andReturn(null);
            EasyMock.expect(gamerAccountDao.findByEmail(email)).andReturn(TEST_ACCOUNT_NEW);
        }

        EasyMock.replay(gamerAccountDao);
        accountLifecycleService.createNewAccount(userName, email, null, null);
        EasyMock.verify(gamerAccountDao);
    }

    @DataProvider
    private Object[][] getData4testCreateNewAccountUserAlreadyExists() {
        return new Object[][] {
            {USER_TEST_NAME, null},
            {USER_TEST_NAME, USER_TEST_EMAIL}
        };
    }

    @Test(dataProvider = "getData4testChangeStatus")
    public void testChangeStatus(GamerAccount gamerAccount, AccountStatus newStatus, AccountOperation accountOperation) {
        EasyMock.expect(gamerAccountDao.findByPrimaryKey(USER_TEST_ID)).andReturn(gamerAccount);
        EasyMock.expect(accountOperationsService.
            startAccountStatusChangeOperation(USER_TEST_ID, gamerAccount.getStatus(), newStatus))
            .andReturn(accountOperation);
        mailPreparationService.prepareVerificationMessageForAccountOperation(gamerAccount.getUserName(),
            gamerAccount.getEmail(), gamerAccount.getLocale(), accountOperation);

        EasyMock.replay(gamerAccountDao, accountOperationsService, mailPreparationService);
        accountLifecycleService.changeStatus(USER_TEST_ID, newStatus);
        EasyMock.verify(gamerAccountDao, accountOperationsService, mailPreparationService);
    }

    @DataProvider
    private Object[][] getData4testChangeStatus() {
        return new Object[][] {
            {TEST_ACCOUNT_NEW, AccountStatus.ACTIVE, AccountOperationDataProvider.STAT_GHNG_NEW_ACT_OP}
        };
    }

    @Test(dataProvider = "getData4testChangeStatusIllegalStatus", expectedExceptions = BusinessException.class)
    public void testChangeStatusIllegalStatus(GamerAccount gamerAccount, AccountStatus newStatus) {
        EasyMock.expect(gamerAccountDao.findByPrimaryKey(USER_TEST_ID)).andReturn(gamerAccount);

        EasyMock.replay(gamerAccountDao);
        accountLifecycleService.changeStatus(USER_TEST_ID, newStatus);
        EasyMock.verify(gamerAccountDao);
    }

    @DataProvider
    private Object[][] getData4testChangeStatusIllegalStatus() {
        return new Object[][] {
            {TEST_ACCOUNT_NEW, AccountStatus.REMOVED},
            {TEST_ACCOUNT_REMOVED_COMPLETELY, AccountStatus.NEW}
        };
    }

    @Test(dataProvider = "getData4testConfirmStatusChange")
    public void testConfirmStatusChange(GamerAccount gamerAccount, String newValue) {
        String code = "code";

        EasyMock.expect(gamerAccountDao.findByPrimaryKey(USER_TEST_ID)).andReturn(gamerAccount);
        EasyMock.expect(accountOperationsService.completeOperation(USER_TEST_ID, OperationType.STATUS_CHANGED, code))
            .andReturn(newValue);
        gamerAccountDao.update(gamerAccount);
        if (newValue.equals(String.valueOf(AccountStatus.REMOVED.ordinal()))) {
            userActivityService.stopUserActivity(gamerAccount.getId());
        }

        EasyMock.replay(gamerAccountDao, accountOperationsService, userActivityService);
        accountLifecycleService.confirmStatusChange(USER_TEST_ID, code);
        EasyMock.verify(gamerAccountDao, accountOperationsService, userActivityService);
    }

    @DataProvider
    private Object[][] getData4testConfirmStatusChange() {
        return new Object[][] {
            {TEST_ACCOUNT_NEW, String.valueOf(AccountStatus.ACTIVE.ordinal())},
            {TEST_ACCOUNT_NEW, String.valueOf(AccountStatus.REMOVED.ordinal())}
        };
    }

    @Test(dataProvider = "getData4testRemoveAccount")
    public void testRemoveAccount(GamerAccount gamerAccount) {
        EasyMock.expect(gamerAccountDao.findByPrimaryKey(USER_TEST_ID)).andReturn(gamerAccount);
        accountOperationsService.cancelUncompletedOperations(USER_TEST_ID);
        gamerAccountDao.update(gamerAccount);

        EasyMock.replay(gamerAccountDao, accountOperationsService);
        accountLifecycleService.removeAccount(USER_TEST_ID);
        EasyMock.verify(gamerAccountDao, accountOperationsService);
    }

    @DataProvider
    private Object[][] getData4testRemoveAccount() {
        return new Object[][] {
            {TEST_ACCOUNT_NEW},
            {TEST_ACCOUNT_REMOVED}
        };
    }
}
