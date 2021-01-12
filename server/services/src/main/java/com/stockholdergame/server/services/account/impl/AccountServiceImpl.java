package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.AccountOperationDao;
import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dao.UserMapperDao;
import com.stockholdergame.server.dto.ConfirmationDto;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.dto.account.AccountOperationDto;
import com.stockholdergame.server.dto.account.MyAccountDto;
import com.stockholdergame.server.dto.account.OperationTypeDto;
import com.stockholdergame.server.dto.account.UserDto;
import com.stockholdergame.server.dto.account.UserFilterDto;
import com.stockholdergame.server.dto.account.UsersList;
import com.stockholdergame.server.dto.mapper.DtoMapper;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.helpers.MD5Helper;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.OperationType;
import com.stockholdergame.server.services.UserInfoAware;
import com.stockholdergame.server.services.account.AccountLifecycleService;
import com.stockholdergame.server.services.account.AccountOperationsService;
import com.stockholdergame.server.services.account.AccountService;
import com.stockholdergame.server.services.mail.MailPreparationService;
import com.stockholdergame.server.services.security.CaptchaService;
import com.stockholdergame.server.session.UserInfo;
import com.stockholdergame.server.session.UserSessionUtil;
import com.stockholdergame.server.util.AccountUtils;
import com.stockholdergame.server.util.security.RandomStringGenerator;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stockholdergame.server.model.account.AccountStatus.ACTIVE;
import static com.stockholdergame.server.model.account.AccountStatus.REMOVED;

/**
 * Account service implementation.
 *
 * @author Alexander Savin
 *         Date: 20.2.2010 17.06.10
 */
@Service
public class AccountServiceImpl extends UserInfoAware implements AccountService {

    public static final int MAX_RESULTS = 100;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private AccountOperationDao accountOperationDao;

    @Autowired
    private UserMapperDao userMapperDao;

    @Autowired
    private MailPreparationService mailPreparationService;

    @Autowired
    private AccountOperationsService operationsService;

    @Autowired
    private AccountLifecycleService accountLifecycleService;

    @Autowired
    private CaptchaService captchaService;

    public void setGamerAccountDao(GamerAccountDao gamerAccountDao) {
        this.gamerAccountDao = gamerAccountDao;
    }

    public void setUserMapperDao(UserMapperDao userMapperDao) {
        this.userMapperDao = userMapperDao;
    }

    public void setMailPreparationService(MailPreparationService mailPreparationService) {
        this.mailPreparationService = mailPreparationService;
    }

    public void setAuditService(AccountOperationsService operationsService) {
        this.operationsService = operationsService;
    }

    /**
     * {@inheritDoc}
     */
    public void registerNewUser(RegistrationDto registrationDto) {
        String userName = registrationDto.getUserName();
        String email = registrationDto.getEmail();
        String password = registrationDto.getPassword();
        String language = registrationDto.getLanguage();

        // CAPTCHA is switched off temporary.
        /*String captchaAnswer = registrationDto.getCaptchaAnswer();
        if (!captchaService.checkAnswer(captchaAnswer)) {
            throw new BusinessException(BusinessExceptionType.CAPTCHA_ANSWER_INCORRECT);
        }*/

        accountLifecycleService.createNewAccount(userName, email.toLowerCase(), password, language);
    }

    public void confirmAccountStatus(ConfirmationDto confirmationDto) {
        AccountOperation accountOperation = accountOperationDao.findUncompletedOperationByCode(confirmationDto.getVerificationCode());
        if (accountOperation != null) {
            Long gamerId = accountOperation.getGamerId();
            GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);

            accountLifecycleService.confirmStatusChange(gamerId, confirmationDto.getVerificationCode());
            updateUserInfo(gamerAccount);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void changePassword(PasswordChangingDto passwordChangingDto) {
        GamerAccount account = findGamerAccount(getCurrentUser().getUserName());
        if (isPasswordCorrect(passwordChangingDto.getOldPassword(), account.getPassword())) {
            account.setPassword(AccountUtils.createHashWithSalt(passwordChangingDto.getNewPassword()));
            gamerAccountDao.update(account);
            operationsService.auditPasswordChange(account.getId());
        } else {
            throw new BusinessException(BusinessExceptionType.INCORRECT_PASSWORD);
        }
    }

    public void changeEmail(String newEmail) {
        if (isEmailUsed(newEmail)) {
            throw new BusinessException(BusinessExceptionType.USER_ALREADY_EXISTS, newEmail);
        }

        UserInfo userInfo = getCurrentUser();

        AccountOperation accountOperation = operationsService.startEmailChangeOperation(userInfo.getId(),
                userInfo.getEmail(), newEmail);

        mailPreparationService.prepareVerificationMessageForEmailConfirmation(userInfo.getUserName(), newEmail,
                userInfo.getLocale(), accountOperation);
    }

    public void confirmNewEmail(ConfirmationDto confirmationDto) {
        AccountOperation accountOperation = accountOperationDao.findUncompletedOperationByCode(confirmationDto.getVerificationCode());
        if (accountOperation != null) {
            Long gamerId = accountOperation.getGamerId();
            GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);
            String newEmail = operationsService.completeOperation(getCurrentUser().getId(), OperationType.EMAIL_CHANGED,
                    confirmationDto.getVerificationCode());
            gamerAccount.setEmail(newEmail);
            gamerAccountDao.update(gamerAccount);
            updateUserInfo(gamerAccount);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean checkUserNameExistence(String userName) {
        return gamerAccountDao.findByUserName(userName) != null;
    }

    /**
     * {@inheritDoc}
     */
    public void resetPassword(String userNameOrEmail) {
        GamerAccount account = findGamerAccount(userNameOrEmail);
        String newPassword = generateRandomString();
        account.setPassword(AccountUtils.createHashWithSalt(newPassword));
        gamerAccountDao.update(account);
        mailPreparationService.prepareResetPasswordMessage(account.getUserName(), account.getEmail(), newPassword,
                account.getLocale());
        operationsService.auditPasswordChange(account.getId());
    }

    /**
     * {@inheritDoc}
     */
    public void removeAccount() {
        accountLifecycleService.changeStatus(getCurrentUser().getId(), REMOVED);
    }

    /**
     * {@inheritDoc}
     */
    public void restoreAccount() {
        accountLifecycleService.changeStatus(getCurrentUser().getId(), ACTIVE);
    }

    /**
     * {@inheritDoc}
     */
    public MyAccountDto getAccountInfo() {
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(getCurrentUser().getId());
        if (gamerAccount == null) {
            throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, getCurrentUser().getUserName());
        }

        List<AccountOperation> accountOperations = operationsService.findUncompletedOperations(getCurrentUser().getId());
        List<AccountOperationDto> accountOperationDtoList = DtoMapper.mapList(accountOperations, AccountOperationDto.class);
        decodeValues(accountOperationDtoList);

        MyAccountDto myAccountDto = DtoMapper.map(gamerAccount, MyAccountDto.class);
        myAccountDto.setAccountOperations(accountOperationDtoList);
        return myAccountDto;
    }

    public UsersList getUsers(UserFilterDto userFilter) {
        if (userFilter.getMaxResults() > MAX_RESULTS) {
            userFilter.setMaxResults(MAX_RESULTS);
        }
        int totalCount = userMapperDao.countUsers(getCurrentUser().getId(), userFilter);
        List< UserDto > users = userMapperDao.findUsers(getCurrentUser().getId(), userFilter);
        return new UsersList(totalCount, fillOnlineFlag(users));
    }

    public void resendConfirmationEmail(OperationTypeDto operationTypeDto) {
        OperationType operationType = OperationType.valueOf(operationTypeDto.getOperationType());
        AccountOperation accountOperation = operationsService.findUncompletedOperationByType(getCurrentUser().getId(),
                operationType);
        if (accountOperation == null) {
            throw new BusinessException(BusinessExceptionType.OPERATION_NOT_FOUND, operationType.getDescription());
        }
        UserInfo userInfo = getCurrentUser();
        mailPreparationService.prepareVerificationMessageForAccountOperation(userInfo.getUserName(),
                userInfo.getEmail(), userInfo.getLocale(), accountOperation);
    }

    public void cancelAccountOperation(OperationTypeDto operationTypeDto) {
        operationsService.cancelUncompletedOperation(getCurrentUser().getId(),
                OperationType.valueOf(operationTypeDto.getOperationType()));
    }

    public boolean checkEmailUsage(String email) {
        return isEmailUsed(email);
    }

    public void changeUserName(String newUserName) {
        if (gamerAccountDao.findByUserName(newUserName) != null) {
            throw new BusinessException(BusinessExceptionType.USER_ALREADY_EXISTS, newUserName);
        }

        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(getCurrentUser().getId());

        operationsService.auditUserNameChange(getCurrentUser().getId(), gamerAccount.getUserName(), newUserName);

        gamerAccount.setUserName(newUserName);
        gamerAccountDao.update(gamerAccount);
    }

    public void changeLanguage(String language) {
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(getCurrentUser().getId());
        if (!LocaleRegistry.isLocaleValid(language)) {
            throw new BusinessException(BusinessExceptionType.UNSUPPORTED_LANGUAGE, language);
        }
        gamerAccount.setLocale(LocaleRegistry.getLocale(language));
        gamerAccountDao.update(gamerAccount);
    }

    void updateUserInfo(GamerAccount gamerAccount) {
        UserSessionUtil.initUserSessionData(gamerAccount);
    }

    private void decodeValues(List<AccountOperationDto> accountOperationDtoList) {
        for (AccountOperationDto accountOperationDto : accountOperationDtoList) {
            if (accountOperationDto.getOperationType().equals(OperationType.STATUS_CHANGED.name())) {
                accountOperationDto.setOldValue(AccountStatus.values()[NumberUtils.toInt(accountOperationDto.getOldValue())].name());
                accountOperationDto.setNewValue(AccountStatus.values()[NumberUtils.toInt(accountOperationDto.getNewValue())].name());
            }
        }
    }

    private List<UserDto> fillOnlineFlag(List<UserDto> users) {
        for (UserDto user : users) {
            user.setOnline(isUserOnline(user.getUserName()));
        }
        return users;
    }

    String generateRandomString() {
        return RandomStringGenerator.generate();
    }

    boolean isPasswordCorrect(String password, String hashedPassword) {
        return MD5Helper.checkMD5hash(password, hashedPassword);
    }

    private boolean isEmailUsed(String email) {
        return gamerAccountDao.findByEmail(email) != null;
    }

    private GamerAccount findGamerAccount(String userNameOrEmail) {
        GamerAccount account = gamerAccountDao.findByUniqueParameters(userNameOrEmail);
        if (account == null) {
            throw new BusinessException(BusinessExceptionType.USER_NOT_FOUND, userNameOrEmail);
        }
        return account;
    }
}
