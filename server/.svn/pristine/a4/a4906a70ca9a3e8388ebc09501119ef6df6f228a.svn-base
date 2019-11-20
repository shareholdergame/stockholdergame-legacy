package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.dao.GamerAccountDao;
import com.stockholdergame.server.dao.ProfileDao;
import com.stockholdergame.server.exceptions.BusinessException;
import com.stockholdergame.server.exceptions.BusinessExceptionType;
import com.stockholdergame.server.helpers.MD5Helper;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.account.GamerAccount;
import com.stockholdergame.server.model.account.OperationType;
import com.stockholdergame.server.model.event.BusinessEventType;
import com.stockholdergame.server.services.account.AccountLifecycleService;
import com.stockholdergame.server.services.account.AccountOperationsService;
import com.stockholdergame.server.services.account.UserActivityService;
import com.stockholdergame.server.services.event.BusinessEventBuilder;
import com.stockholdergame.server.services.event.impl.AbstractEventPublisher;
import com.stockholdergame.server.services.mail.MailPreparationService;
import com.stockholdergame.server.session.UserSessionData;
import com.stockholdergame.server.session.UserSessionUtil;
import com.stockholdergame.server.util.AccountUtils;
import com.stockholdergame.server.util.collections.MapBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.stockholdergame.server.dao.util.IdentifierHelper.generateLongId;
import static com.stockholdergame.server.helpers.MessagingHelper.generateSubtopicName;
import static com.stockholdergame.server.model.account.AccountStatus.ACTIVE;
import static com.stockholdergame.server.model.account.AccountStatus.NEW;

/**
 * @author Alexander Savin
 *         Date: 25.12.11 14.21
 */
@Service
public class AccountLifecycleServiceImpl extends AbstractEventPublisher implements AccountLifecycleService {

    public static final String REMOVED_USER_NAME = "Removed user";

    public static final String REMOVED_MARKER = "[removed]";

    private Map<AccountStatus, List<AccountStatus>> lifeCycleMap;

    @Autowired
    private GamerAccountDao gamerAccountDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private MailPreparationService mailPreparationService;

    @Autowired
    private AccountOperationsService operationsService;

    @Autowired
    private UserActivityService userActivityService;

    public void setGamerAccountDao(GamerAccountDao gamerAccountDao) {
        this.gamerAccountDao = gamerAccountDao;
    }

    public void setMailPreparationService(MailPreparationService mailPreparationService) {
        this.mailPreparationService = mailPreparationService;
    }

    public void setOperationsService(AccountOperationsService operationsService) {
        this.operationsService = operationsService;
    }

    public void setUserActivityService(UserActivityService userActivityService) {
        this.userActivityService = userActivityService;
    }

    public AccountLifecycleServiceImpl() {
        lifeCycleMap = new MapBuilder<AccountStatus, List<AccountStatus>>()
                .append(AccountStatus.NEW, Arrays.asList(AccountStatus.ACTIVE, AccountStatus.REMOVED_COMPLETELY))
                .append(AccountStatus.ACTIVE, Collections.singletonList(AccountStatus.REMOVED))
                .append(AccountStatus.REMOVED, Arrays.asList(AccountStatus.ACTIVE, AccountStatus.REMOVED_COMPLETELY))
                .toHashMap();
    }

    public void createNewAccount(String userName, String email, String password, String language) {
        if (isUserNameExists(userName)) {
            throw new BusinessException(BusinessExceptionType.USER_ALREADY_EXISTS, userName);
        }
        if (isEmailUsed(email)) {
            throw new BusinessException(BusinessExceptionType.USER_ALREADY_EXISTS, email);
        }

        Locale userLocale = LocaleRegistry.getDefaultLocale();
        if (LocaleRegistry.isLocaleValid(language)) {
            userLocale = LocaleRegistry.getLocale(language);
        }

        Date registrationDate = new Date();

        GamerAccount gamerAccount = new GamerAccount();
        gamerAccount.setId(generateLongId());
        gamerAccount.setUserName(userName);
        gamerAccount.setPassword(AccountUtils.createHashWithSalt(password));
        gamerAccount.setEmail(email);
        gamerAccount.setRegistrationDate(registrationDate);
        gamerAccount.setLocale(userLocale);
        gamerAccount.setStatus(NEW);
        gamerAccount.setSubtopicName(generateSubtopicName());
        gamerAccount.setIsBot(false);

        gamerAccountDao.create(gamerAccount);

        AccountOperation accountOperation = operationsService.startAccountStatusChangeOperation(gamerAccount.getId(),
                NEW, ACTIVE);

        mailPreparationService.prepareVerificationMessageForAccountOperation(userName, email, gamerAccount.getLocale(),
                accountOperation);

        if (UserSessionUtil.getCurrentSession() == null) {
            UserSessionUtil.initUserSessionData(gamerAccount);
        }

        publishEvent(BusinessEventBuilder.<UserSessionData>initBuilder()
            .setType(BusinessEventType.ACCOUNT_CREATED).setInitiatorId(gamerAccount.getId())
            .setPayload(UserSessionUtil.getCurrentSession()).toEvent());
    }

    public void changeStatus(Long gamerId, AccountStatus toStatus) {
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);
        AccountStatus currentStatus = gamerAccount.getStatus();
        if (isNewStatusAvailable(currentStatus, toStatus)) {
            AccountOperation accountOperation = operationsService.startAccountStatusChangeOperation(gamerId,
                    currentStatus, toStatus);

            mailPreparationService.prepareVerificationMessageForAccountOperation(gamerAccount.getUserName(),
                    gamerAccount.getEmail(), gamerAccount.getLocale(), accountOperation);
        } else {
            throw new BusinessException(BusinessExceptionType.ILLEGAL_ACCOUNT_STATUS, currentStatus.getDescription());
        }
    }

    public void confirmStatusChange(Long gamerId, String verificationCode) {
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);
        AccountStatus newStatus = AccountStatus.values()[NumberUtils.toInt(
                operationsService.completeOperation(gamerId, OperationType.STATUS_CHANGED, verificationCode))];

        gamerAccount.setStatus(newStatus);
        if (AccountStatus.REMOVED.equals(newStatus)) {
            gamerAccount.setRemovalDate(AccountUtils.calculateRemovalDate(new Date()));
            stopUserActivity(gamerId);
        } else {
            gamerAccount.setRemovalDate(null);
        }
        gamerAccountDao.update(gamerAccount);
    }

    private void stopUserActivity(Long gamerId) {
        userActivityService.stopUserActivity(gamerId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeAccount(Long gamerId) {
        GamerAccount gamerAccount = gamerAccountDao.findByPrimaryKey(gamerId);
        if (AccountStatus.REMOVED.equals(gamerAccount.getStatus())
                || AccountStatus.NEW.equals(gamerAccount.getStatus())) {
            cancelOperations(gamerId);
            removeAccount(gamerAccount);
        }
    }

    private void removeAccount(GamerAccount gamerAccount) {
        if (gamerAccount.getProfile() != null) {
            profileDao.remove(gamerAccount.getProfile());
            gamerAccount.setProfile(null);
        }
        gamerAccount.setUserName(REMOVED_USER_NAME);
        gamerAccount.setEmail(generateSurrogateValue(gamerAccount.getEmail()));
        gamerAccount.setStatus(AccountStatus.REMOVED_COMPLETELY);
        gamerAccount.setPassword(generateSurrogateValue(gamerAccount.getPassword()));
        gamerAccount.setSubtopicName(generateSurrogateValue(gamerAccount.getSubtopicName()));
        gamerAccount.setRemovalDate(new Date());
        gamerAccountDao.update(gamerAccount);
    }

    private String generateSurrogateValue(String userName) {
        return REMOVED_MARKER + "_" + MD5Helper.generateMD5hash(userName + System.currentTimeMillis());
    }

    private void cancelOperations(Long gamerId) {
        operationsService.cancelUncompletedOperations(gamerId);
    }

    private boolean isNewStatusAvailable(AccountStatus currentStatus, AccountStatus toStatus) {
        return lifeCycleMap.containsKey(currentStatus) && lifeCycleMap.get(currentStatus).contains(toStatus);
    }

    private boolean isUserNameExists(String userName) {
        return gamerAccountDao.findByUserName(userName) != null;
    }

    private boolean isEmailUsed(String email) {
        return gamerAccountDao.findByEmail(email) != null;
    }
}
