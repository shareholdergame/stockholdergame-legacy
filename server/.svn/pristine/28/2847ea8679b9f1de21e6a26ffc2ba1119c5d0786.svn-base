package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.services.account.AccountOperationsService;
import com.stockholdergame.server.services.mail.MailPreparationService;
import com.stockholdergame.server.session.UserInfo;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang.time.DateUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.stockholdergame.server.model.account.AccountStatus.*;
import static org.easymock.EasyMock.*;

/**
 * @author Alexander Savin
 *         Date: 28.3.2010 15.44.39
 */
@Test
public class AccountServiceTest {

    private AccountServiceImpl accountService;
    private MailPreparationService mailService;
    private GamerAccountDao gamerAccountDao;
    private AccountOperationsService operationsService;

    private static final String USER = "user";
    private static final String USER_EMAIL = "user@some.com";
    private static final String PASSWORD = "password";
    private static final Locale LANG = Locale.ENGLISH;

    @BeforeMethod
    public void setUp() {
        accountService = new AccountServiceImpl() {

            @Override
            String generateRandomString() {
                return PASSWORD;
            }

            @Override
            boolean isPasswordCorrect(String password, String hashedPassword) {
                return (password == null && hashedPassword == null)
                    || (password != null && password.equals(hashedPassword));
            }

            @Override
            protected UserInfo getCurrentUser() {
                return new UserInfo(1L, USER, USER_EMAIL, Locale.ENGLISH, AccountStatus.ACTIVE, "subtopic");
            }
        };
        mailService = createMock(MailPreparationService.class);
        gamerAccountDao = createMock(GamerAccountDao.class);
        operationsService = createMock(AccountOperationsService.class);
        accountService.setMailPreparationService(mailService);
        accountService.setGamerAccountDao(gamerAccountDao);
        accountService.setAuditService(operationsService);
    }

    @Test(enabled = false)
    public void testRegisterNewUser() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUserName(USER);
        registrationDto.setEmail(USER_EMAIL);
        registrationDto.setPassword(PASSWORD);
        registrationDto.setLanguage(LANG.getLanguage());

        GamerAccount ga = createGamerAccount();

        expect(gamerAccountDao.findByUserName(USER)).andReturn(null);
        expect(gamerAccountDao.findByEmail(USER_EMAIL)).andReturn(null);
        gamerAccountDao.create(ga);

        replay(gamerAccountDao, mailService);
        accountService.registerNewUser(registrationDto);
        verify(gamerAccountDao, mailService);
    }

    public void testChangePassword() {
        PasswordChangingDto passwordChangingDto = new PasswordChangingDto();
        passwordChangingDto.setOldPassword(PASSWORD);
        passwordChangingDto.setNewPassword("new_password");

        GamerAccount ga = createGamerAccount();
        ga.setId(1L);

        expect(gamerAccountDao.findByUniqueParameters(USER)).andReturn(ga);
        gamerAccountDao.update(ga);
        operationsService.auditPasswordChange(ga.getId());

        replay(gamerAccountDao, operationsService);
        accountService.changePassword(passwordChangingDto);
        verify(gamerAccountDao, operationsService);
    }

    public void testResetPassword() {
        GamerAccount ga = createGamerAccount();
        ga.setId(1L);

        expect(gamerAccountDao.findByUniqueParameters(USER_EMAIL)).andReturn(ga);
        gamerAccountDao.update(ga);
        mailService.prepareResetPasswordMessage(ga.getUserName(), ga.getEmail(), ga.getPassword(),
            ga.getLocale());
        operationsService.auditPasswordChange(ga.getId());

        replay(gamerAccountDao, mailService, operationsService);
        accountService.resetPassword(USER_EMAIL);
        verify(gamerAccountDao, mailService, operationsService);
    }

    @Test(enabled = false)
    public void testRemoveAccount() {
        GamerAccount ga = createGamerAccount();
        ga.setStatus(ACTIVE);
        ga.setId(1L);

        expect(gamerAccountDao.findByUniqueParameters(USER)).andReturn(ga);

        replay(gamerAccountDao, mailService);
        accountService.removeAccount();
        verify(gamerAccountDao, mailService);
    }

    @Test(enabled = false)
    public void testRestoreAccount() {
        GamerAccount ga = createGamerAccount();
        ga.setStatus(REMOVED);
        ga.setId(1L);

        expect(gamerAccountDao.findByUniqueParameters(USER)).andReturn(ga);
        //expect(operationsService.isAccountOperationAlreadyInitiated(1L)).andReturn(false);
        //expect(operationsService.startAccountStatusChangeOperation(1L, AccountStatus.REMOVED, AccountStatus.ACTIVE, ))

        replay(gamerAccountDao);
        accountService.restoreAccount();
        verify(gamerAccountDao);
    }

    private GamerAccount createGamerAccount() {
        GamerAccount ga = new GamerAccount();
        ga.setUserName(USER);
        ga.setEmail(USER_EMAIL);
        ga.setPassword(PASSWORD);
        ga.setStatus(NEW);
        ga.setRegistrationDate(DateUtils.truncate(new Date(), Calendar.DATE));
        ga.setLocale(LANG);
        return ga;
    }
}
